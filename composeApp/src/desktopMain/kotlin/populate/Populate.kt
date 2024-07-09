package populate

import framework.*
import impl.getDashboardStats
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils.create
import org.jetbrains.exposed.sql.transactions.transaction
import java.math.BigDecimal
import java.time.LocalDate
import kotlin.random.Random

fun populateTransactionHistory(userId: Int) = transaction {
    val incomeHeads = listOf(
        IncomeHead.new {
            user = User.findById(userId)!!
            name = "Salary"
            frequency = "Monthly"
        },
        IncomeHead.new {
            user = User.findById(userId)!!
            name = "Freelance"
            frequency = "Weekly"
        },
        IncomeHead.new {
            user = User.findById(userId)!!
            name = "Investment"
            frequency = "Monthly"
        }
    )

    val expenseHeads = listOf(
        ExpenseHead.new {
            user = User.findById(userId)!!
            name = "Rent"
            frequency = "Monthly"
        },
        ExpenseHead.new {
            user = User.findById(userId)!!
            name = "Utilities"
            frequency = "Monthly"
        },
        ExpenseHead.new {
            user = User.findById(userId)!!
            name = "Groceries"
            frequency = "Weekly"
        },
        ExpenseHead.new {
            user = User.findById(userId)!!
            name = "Entertainment"
            frequency = "Monthly"
        },
        ExpenseHead.new {
            user = User.findById(userId)!!
            name = "Transport"
            frequency = "Daily"
        }
    )

    val startDate = LocalDate.now().minusMonths(3)
    val endDate = LocalDate.now()

    // Generate random transactions for the past 3 months
    for (date in startDate..endDate) {
        if (Random.nextBoolean()) {
            // Add income transaction
            Income.new {
                this.user = User.findById(userId)!!
                this.date = date
                this.head = incomeHeads.random()
                this.amount = BigDecimal(Random.nextInt(1000, 5000))
                this.description = "Income on $date"
            }
        }
        if (Random.nextBoolean()) {
            // Add expense transaction
            Expense.new {
                this.user = User.findById(userId)!!
                this.date = date
                this.head = expenseHeads.random()
                this.amount = BigDecimal(Random.nextInt(100, 2000))
                this.description = "Expense on $date"
            }
        }
    }
}

// Utility function to iterate over date range
operator fun LocalDate.rangeTo(other: LocalDate) = generateSequence(this) { it.plusDays(1) }.takeWhile { it <= other }


fun main() {
    Database.connect("jdbc:sqlite:C:\\Users\\Harshith\\IdeaProjects\\PettyCash Desktop\\composeApp\\src\\commonMain\\composeResources\\files\\test.db", driver = "org.sqlite.JDBC")

    transaction {
        create(Users, IncomeHeads, Incomes, ExpenseHeads, Expenses)
    }

    // Assuming Harshith's user ID is 1 (you might need to query the user ID if it's different)
    transaction {
        val harshith = User.find { Users.username eq "hatitu" }.single()
        val harshithId = harshith.id.value
        harshith.clearTransactionHistory()
        populateTransactionHistory(harshithId)

        val dashboardStats = getDashboardStats(harshithId)
        println("Total Income: ${dashboardStats.totalIncome}")
        println("Total Expenses: ${dashboardStats.totalExpenses}")
        println("Current Balance: ${dashboardStats.currentBalance}")
        dashboardStats.recentTransactions.forEach {
            println("Transaction - Date: ${it.date}, Amount: ${it.amount}, Type: ${it.type}, Head: ${it.head}, Description: ${it.description}")
        }
    }
}
