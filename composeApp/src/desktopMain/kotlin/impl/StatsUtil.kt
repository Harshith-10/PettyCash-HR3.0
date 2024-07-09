@file:Suppress("unused")

package impl

import framework.ExpenseHeads
import framework.Expenses
import framework.Incomes
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.javatime.month
import org.jetbrains.exposed.sql.javatime.year
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.sum
import org.jetbrains.exposed.sql.transactions.transaction
import java.math.BigDecimal
import java.time.LocalDate

data class MonthlyReport(
    val month: String,
    val totalIncome: BigDecimal,
    val totalExpenses: BigDecimal
)

data class YearlyOverview(
    val year: Int,
    val totalIncome: BigDecimal,
    val totalExpenses: BigDecimal
)

data class CategoryWiseSpending(
    val category: String,
    val totalAmount: BigDecimal
)

data class TrendsAndPatterns(
    val date: LocalDate,
    val balance: BigDecimal
)

fun getMonthlyReport(userId: Int, year: Int): List<MonthlyReport> = transaction {
    (1..12).map { month ->
        val totalIncome = Incomes.select(Incomes.amount.sum())
            .where { (Incomes.user eq userId) and (Incomes.date.year() eq year) and (Incomes.date.month() eq month) }
            .firstOrNull()?.get(Incomes.amount.sum()) ?: BigDecimal.ZERO

        val totalExpenses = Expenses.select(Expenses.amount.sum())
            .where { (Expenses.user eq userId) and (Expenses.date.year() eq year) and (Expenses.date.month() eq month) }
            .firstOrNull()?.get(Expenses.amount.sum()) ?: BigDecimal.ZERO

        MonthlyReport(
            month = LocalDate.of(year, month, 1).month.name,
            totalIncome = totalIncome,
            totalExpenses = totalExpenses
        )
    }
}

fun getYearlyOverview(userId: Int, startYear: Int, endYear: Int): List<YearlyOverview> = transaction {
    (startYear..endYear).map { year ->
        val totalIncome = Incomes.select(Incomes.amount.sum())
            .where { (Incomes.user eq userId) and (Incomes.date.year() eq year) }
            .firstOrNull()?.get(Incomes.amount.sum()) ?: BigDecimal.ZERO

        val totalExpenses = Expenses.select(Expenses.amount.sum())
            .where { (Expenses.user eq userId) and (Expenses.date.year() eq year) }
            .firstOrNull()?.get(Expenses.amount.sum()) ?: BigDecimal.ZERO

        YearlyOverview(
            year = year,
            totalIncome = totalIncome,
            totalExpenses = totalExpenses
        )
    }
}

fun getCategoryWiseSpending(userId: Int): List<CategoryWiseSpending> = transaction {
    val expenseHeads = ExpenseHeads.selectAll().where { ExpenseHeads.user eq userId }.map { it[ExpenseHeads.name] }

    expenseHeads.map { head ->
        val totalAmount = Expenses.select(Expenses.amount.sum())
            .where { (Expenses.user eq userId) and (ExpenseHeads.name eq head) }
            .firstOrNull()?.get(Expenses.amount.sum()) ?: BigDecimal.ZERO

        CategoryWiseSpending(
            category = head,
            totalAmount = totalAmount
        )
    }
}

fun getTrendsAndPatterns(userId: Int): List<TrendsAndPatterns> = transaction {
    (Incomes.selectAll().where { Incomes.user eq userId } + Expenses.selectAll().where { Expenses.user eq userId })
        .sortedBy { it[Incomes.date] ?: it[Expenses.date] }
        .map {
            TrendsAndPatterns(
                date = it[Incomes.date] ?: it[Expenses.date]!!,
                balance = (Incomes.select(Incomes.amount.sum())
                    .where {
                        (Incomes.user eq userId) and (Incomes.date lessEq (it[Incomes.date] ?: it[Expenses.date]!!))
                    }.firstOrNull()?.get(Incomes.amount.sum()) ?: BigDecimal.ZERO) - (Expenses.select(Expenses.amount.sum())
                        .where {
                            (Expenses.user eq userId) and (Expenses.date lessEq (it[Incomes.date] ?: it[Expenses.date]!!))
                        }.firstOrNull()?.get(Expenses.amount.sum()) ?: BigDecimal.ZERO)
            )
        }
}
