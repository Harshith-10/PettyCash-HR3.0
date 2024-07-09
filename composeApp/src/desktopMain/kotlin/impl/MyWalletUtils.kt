@file:Suppress("unused")

package impl

import framework.*
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.sum
import org.jetbrains.exposed.sql.transactions.transaction
import java.math.BigDecimal
import java.time.LocalDate

data class WalletInfo(
    val currentBalance: BigDecimal,
    val recentTransactions: List<Transaction>
)

fun getWalletInfo(userId: Int): WalletInfo = transaction {
    val totalIncome = Incomes.select(Incomes.amount.sum()).where { Incomes.user eq userId }.firstOrNull()
        ?.get(Incomes.amount.sum()) ?: BigDecimal.ZERO
    val totalExpenses =
        Expenses.select(Expenses.amount.sum()).where { Expenses.user eq userId }.firstOrNull()
            ?.get(Expenses.amount.sum()) ?: BigDecimal.ZERO

    val currentBalance = totalIncome - totalExpenses

    val recentTransactions =
        (Incomes.selectAll().where { Incomes.user eq userId } + Expenses.selectAll().where { Expenses.user eq userId })
            .sortedByDescending { it[Incomes.date] ?: it[Expenses.date] }
            .take(10)
            .map {
                Transaction(
                    id = it[Incomes.id]?.value ?: it[Expenses.id]!!.value,
                    date = it[Incomes.date] ?: it[Expenses.date]!!,
                    amount = it[Incomes.amount] ?: it[Expenses.amount]!!,
                    type = if (it.hasValue(Incomes.id)) "Income" else "Expense",
                    head = (IncomeHeads.selectAll().where { IncomeHeads.id eq it[Incomes.head] }.firstOrNull()
                        ?.get(IncomeHeads.name))
                        ?: (ExpenseHeads.selectAll().where { ExpenseHeads.id eq it[Expenses.head] }.firstOrNull()
                            ?.get(ExpenseHeads.name)) ?: "",
                    description = it[Incomes.description] ?: it[Expenses.description]!!
                )
            }

    WalletInfo(
        currentBalance = currentBalance,
        recentTransactions = recentTransactions
    )
}

fun addIncome(userId: Int, date: LocalDate, headId: Int, amount: BigDecimal, description: String) = transaction {
    Income.new {
        user = User.findById(userId)!!
        this.date = date
        this.head = IncomeHead.findById(headId)!!
        this.amount = amount
        this.description = description
    }
}

fun addExpense(userId: Int, date: LocalDate, headId: Int, amount: BigDecimal, description: String) = transaction {
    Expense.new {
        user = User.findById(userId)!!
        this.date = date
        this.head = ExpenseHead.findById(headId)!!
        this.amount = amount
        this.description = description
    }
}
