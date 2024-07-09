@file:Suppress("unused")

package impl

import framework.*
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.math.BigDecimal
import java.time.LocalDate

data class Transaction(
    val id: Int,
    val date: LocalDate,
    val amount: BigDecimal,
    val type: String,
    val head: String,
    val description: String
)

fun getAllTransactions(userId: Int): List<Transaction> = transaction {
    val incomes = Incomes.selectAll().where { Incomes.user eq userId }.map {
        Transaction(
            id = it[Incomes.id].value,
            date = it[Incomes.date],
            amount = it[Incomes.amount],
            type = "Income",
            head = IncomeHeads.selectAll().where { IncomeHeads.id eq it[Incomes.head] }.first()[IncomeHeads.name],
            description = it[Incomes.description]
        )
    }

    val expenses = Expenses.selectAll().where { Expenses.user eq userId }.map {
        Transaction(
            id = it[Expenses.id].value,
            date = it[Expenses.date],
            amount = it[Expenses.amount],
            type = "Expense",
            head = ExpenseHeads.selectAll().where { ExpenseHeads.id eq it[Expenses.head] }.first()[ExpenseHeads.name],
            description = it[Expenses.description]
        )
    }

    (incomes + expenses).sortedByDescending { it.date }
}

fun filterTransactions(userId: Int, dateRange: ClosedRange<LocalDate>?, amountRange: ClosedRange<BigDecimal>?, incomeHeads: List<String>?, expenseHeads: List<String>?): List<Transaction> = transaction {
    var query = (Incomes innerJoin Expenses).selectAll().where {
        (Incomes.user eq userId) or (Expenses.user eq userId)
    }

    dateRange?.let {
        query = query.andWhere { (Incomes.date greaterEq it.start) and (Incomes.date lessEq it.endInclusive) }
    }

    amountRange?.let {
        query = query.andWhere { (Incomes.amount greaterEq it.start) and (Incomes.amount lessEq it.endInclusive) }
    }

    incomeHeads?.let {
        val headIds = IncomeHeads.selectAll().where { IncomeHeads.name inList it }.map { it[IncomeHeads.id].value }
        query = query.andWhere { Incomes.head inList headIds }
    }

    expenseHeads?.let {
        val headIds = ExpenseHeads.selectAll().where { ExpenseHeads.name inList it }.map { it[ExpenseHeads.id].value }
        query = query.andWhere { Expenses.head inList headIds }
    }

    query.map {
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
    }.sortedByDescending { it.date }
}

fun addIncomeHead(userId: Int, name: String, frequency: String) = transaction {
    IncomeHead.new {
        user = User.findById(userId)!!
        this.name = name
        this.frequency = frequency
    }
}

fun addExpenseHead(userId: Int, name: String, frequency: String) = transaction {
    ExpenseHead.new {
        user = User.findById(userId)!!
        this.name = name
        this.frequency = frequency
    }
}
