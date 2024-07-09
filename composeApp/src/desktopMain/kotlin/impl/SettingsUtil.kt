@file:Suppress("unused")

package impl

import framework.*
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.security.crypto.bcrypt.BCrypt

fun getUserProfile(userId: Int): User? = transaction {
    User.findById(userId)
}

fun updateUserProfile(userId: Int, name: String, email: String, password: String?) = transaction {
    val user = User.findById(userId) ?: return@transaction
    user.name = name
    user.email = email
    if (password != null) {
        user.password = BCrypt.hashpw(password, BCrypt.gensalt())
    }
}

fun setDefaultCurrency(userId: Int, currency: String) {
    // This function is a placeholder since the current database schema doesn't include currency
    // Add a column to the Users table if needed
}

fun configureNotificationSettings(userId: Int, settings: Map<String, Any>) {
    // This function is a placeholder since the current database schema doesn't include notification settings
    // Add a column to the Users table if needed
}

fun exportData(userId: Int): String = transaction {
    val incomes = Incomes.selectAll().where { Incomes.user eq userId }.map {
        "Income,${it[Incomes.date]},${it[Incomes.amount]},${
            IncomeHeads.selectAll().where { IncomeHeads.id eq it[Incomes.head] }.first()[IncomeHeads.name]
        },${it[Incomes.description]}"
    }

    val expenses = Expenses.selectAll().where { Expenses.user eq userId }.map {
        "Expense,${it[Expenses.date]},${it[Expenses.amount]},${
            ExpenseHeads.selectAll().where { ExpenseHeads.id eq it[Expenses.head] }.first()[ExpenseHeads.name]
        },${it[Expenses.description]}"
    }

    (incomes + expenses).joinToString("\n")
}
