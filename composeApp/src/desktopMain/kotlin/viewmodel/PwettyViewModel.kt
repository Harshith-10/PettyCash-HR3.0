package viewmodel
import androidx.lifecycle.ViewModel
import framework.User
import impl.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.jetbrains.exposed.sql.Database
import java.math.BigDecimal
import java.time.LocalDate

sealed class Screen {
    data object Dashboard : Screen()
    data object Transactions : Screen()
    data object MyWallet : Screen()
    data object Stats : Screen()
    data object Settings : Screen()
}

class PwettyViewModel: ViewModel() {
    private var _selectedScreen: MutableStateFlow<Screen> = MutableStateFlow(Screen.Dashboard)
    val selectedScreen: StateFlow<Screen> = _selectedScreen.asStateFlow()

    private var _loggedInUser: MutableStateFlow<User?> = MutableStateFlow(null)
    val loggedInUser: StateFlow<User?> = _loggedInUser.asStateFlow()

    private var _error = MutableStateFlow("")
    val error: StateFlow<String> = _error.asStateFlow()

    private var _dashboardStats = MutableStateFlow<DashboardStats?>(null)
    val dashboardStats: StateFlow<DashboardStats?> = _dashboardStats.asStateFlow()

    private var _monthlyChange = MutableStateFlow<Triple<BigDecimal, BigDecimal, BigDecimal>?>(null)
    val monthlyChange: StateFlow<Triple<BigDecimal, BigDecimal, BigDecimal>?> = _monthlyChange.asStateFlow()

    private var _transactions = MutableStateFlow<List<Transaction>>(emptyList())
    val transactions: StateFlow<List<Transaction>> = _transactions.asStateFlow()

    init {
        Database.connect("jdbc:sqlite:C:\\Users\\Harshith\\IdeaProjects\\PettyCash Desktop\\composeApp\\src\\commonMain\\composeResources\\files\\test.db", driver = "org.sqlite.JDBC")
    }

    fun selectScreen(screen: Screen) {
        _selectedScreen.update { screen }
    }

    fun register(
        name: String,
        username: String,
        email: String,
        password: String
    ) {
        val user = User.register(name, username, email, password)
        if (user != null) {
            _loggedInUser.update { user }
            _error.update { "" }
            return
        }
        _error.update { "Username or email already exists" }
    }

    fun login(
        username: String,
        password: String
    ) {
        val user = User.login(username, password)
        if (user != null) {
            _loggedInUser.update { user }
            _error.update { "" }
            refreshDashboardStats()
            getAllTransactions()
            return
        }
        _error.update { "Invalid username or password" }
    }

    fun logout() {
        _loggedInUser.update { null }
    }

    fun deleteAccount() {
        _loggedInUser.value?.let {
            User.deleteAccount(it.id.value)
            logout()
        }
    }

    fun refreshDashboardStats() {
        _loggedInUser.value?.let {
            val stats = getDashboardStats(it.id.value)
            _dashboardStats.update { stats }
            refreshMonthlyChange()
        }
    }

    private fun refreshMonthlyChange() {
        _loggedInUser.value?.let {
            val change = getBalanceChange(it.id.value)
            _monthlyChange.update { change }
        }
    }

    fun getAllTransactions() {
        _loggedInUser.value?.let {
            val transactions = getAllTransactions(it.id.value)
            _transactions.update { transactions }
        }
    }

    fun filterTransactions(
        dateRange: ClosedRange<LocalDate>?,
        amountRange: ClosedRange<BigDecimal>?,
        incomeHeads: List<String>?,
        expenseHeads: List<String>?
    ) {
        _loggedInUser.value?.let {
            val transactions = filterTransactions(it.id.value, dateRange, amountRange, incomeHeads, expenseHeads)
            _transactions.update { transactions }
        }
    }
}