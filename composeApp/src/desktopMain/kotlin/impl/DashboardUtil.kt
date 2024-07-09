@file:Suppress("unused")

package impl

import framework.ExpenseHeads
import framework.Expenses
import framework.IncomeHeads
import framework.Incomes
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.javatime.month
import org.jetbrains.exposed.sql.javatime.year
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.sum
import org.jetbrains.exposed.sql.transactions.transaction
import java.math.BigDecimal
import java.time.LocalDate

data class DashboardStats(
    val totalIncome: BigDecimal,
    val totalExpenses: BigDecimal,
    val currentBalance: BigDecimal,
    val recentTransactions: List<TransactionSummary>
)

data class TransactionSummary(
    val date: LocalDate,
    val amount: BigDecimal,
    val type: String,
    val head: String,
    val description: String
)

fun getDashboardStats(userId: Int): DashboardStats = transaction {
    // Calculate total income
    val totalIncome = Incomes.select(Incomes.amount.sum())
        .where { Incomes.user eq userId }
        .firstOrNull()?.get(Incomes.amount.sum()) ?: BigDecimal.ZERO

    // Calculate total expenses
    val totalExpenses = Expenses.select(Expenses.amount.sum())
        .where { Expenses.user eq userId }
        .firstOrNull()?.get(Expenses.amount.sum()) ?: BigDecimal.ZERO

    // Calculate current balance
    val currentBalance = totalIncome - totalExpenses

    // Fetch recent transactions (last 5 transactions)
    val recentTransactions = (Incomes.selectAll().where { Incomes.user eq userId }.map {
        TransactionSummary(
            date = it[Incomes.date],
            amount = it[Incomes.amount],
            type = "Income",
            head = IncomeHeads.selectAll().where { IncomeHeads.id eq it[Incomes.head] }.first()[IncomeHeads.name],
            description = it[Incomes.description]
        )
    } + Expenses.selectAll().where { Expenses.user eq userId }.map {
        TransactionSummary(
            date = it[Expenses.date],
            amount = it[Expenses.amount],
            type = "Expense",
            head = ExpenseHeads.selectAll().where { ExpenseHeads.id eq it[Expenses.head] }.first()[ExpenseHeads.name],
            description = it[Expenses.description]
        )
    }).sortedByDescending { it.date }//.take(10)

    DashboardStats(
        totalIncome = totalIncome,
        totalExpenses = totalExpenses,
        currentBalance = currentBalance,
        recentTransactions = recentTransactions
    )
}

// Get % of balance, income and expenditure changes compared to last month
fun getBalanceChange(userId: Int): Triple<BigDecimal, BigDecimal, BigDecimal> = transaction {
    val currentMonth = LocalDate.now().monthValue
    val currentYear = LocalDate.now().year

    val lastMonthIncome = Incomes.select(Incomes.amount.sum())
        .where { (Incomes.user eq userId) and (Incomes.date.year() eq currentYear) and (Incomes.date.month() eq currentMonth - 1) }
        .firstOrNull()?.get(Incomes.amount.sum()) ?: BigDecimal.ZERO

    val lastMonthExpenses = Expenses.select(Expenses.amount.sum())
        .where { (Expenses.user eq userId) and (Expenses.date.year() eq currentYear) and (Expenses.date.month() eq currentMonth - 1) }
        .firstOrNull()?.get(Expenses.amount.sum()) ?: BigDecimal.ZERO

    val lastMonthBalance = lastMonthIncome - lastMonthExpenses

    val currentMonthIncome = Incomes.select(Incomes.amount.sum())
        .where { (Incomes.user eq userId) and (Incomes.date.year() eq currentYear) and (Incomes.date.month() eq currentMonth) }
        .firstOrNull()?.get(Incomes.amount.sum()) ?: BigDecimal.ZERO

    val currentMonthExpenses = Expenses.select(Expenses.amount.sum())
        .where { (Expenses.user eq userId) and (Expenses.date.year() eq currentYear) and (Expenses.date.month() eq currentMonth) }
        .firstOrNull()?.get(Expenses.amount.sum()) ?: BigDecimal.ZERO

    val currentMonthBalance = currentMonthIncome - currentMonthExpenses

    val balanceChange = if (lastMonthBalance != BigDecimal.ZERO) ((currentMonthBalance - lastMonthBalance) / lastMonthBalance) * BigDecimal(100) else BigDecimal.ZERO
    val incomeChange = if (lastMonthIncome != BigDecimal.ZERO) ((currentMonthIncome - lastMonthIncome) / lastMonthIncome) * BigDecimal(100) else BigDecimal.ZERO
    val expenseChange = if (lastMonthExpenses != BigDecimal.ZERO) ((currentMonthExpenses - lastMonthExpenses) / lastMonthExpenses) * BigDecimal(100) else BigDecimal.ZERO

    Triple(balanceChange, incomeChange, expenseChange)
}