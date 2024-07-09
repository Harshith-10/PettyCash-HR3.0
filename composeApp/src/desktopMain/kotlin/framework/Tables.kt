package framework

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.date

object Users : IntIdTable() {
    val name = varchar("name", 50)
    val username = varchar("username", 50).uniqueIndex()
    val email = varchar("email", 100).uniqueIndex() // Added email field
    val password = varchar("password", 64) // Storing hashed passwords
}

object IncomeHeads : IntIdTable() {
    val user = reference("user", Users)
    val name = varchar("name", 50)
    val frequency = varchar("frequency", 50) // Could be Enum for better type safety
}

object Incomes : IntIdTable() {
    val user = reference("user", Users)
    val date = date("date")
    val head = reference("head", IncomeHeads)
    val amount = decimal("amount", 10, 2)
    val description = varchar("description", 255)
}

object ExpenseHeads : IntIdTable() {
    val user = reference("user", Users)
    val name = varchar("name", 50)
    val frequency = varchar("frequency", 50) // Could be Enum for better type safety
}

object Expenses : IntIdTable() {
    val user = reference("user", Users)
    val date = date("date")
    val head = reference("head", ExpenseHeads)
    val amount = decimal("amount", 10, 2)
    val description = varchar("description", 255)
}
