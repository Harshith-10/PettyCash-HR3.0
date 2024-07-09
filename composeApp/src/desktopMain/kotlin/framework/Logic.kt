package framework

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.security.crypto.bcrypt.BCrypt

class User(id: EntityID<Int>) : IntEntity(id) {
    var name by Users.name
    var username by Users.username
    var email by Users.email
    var password by Users.password

    // Function to clear transaction history
    fun clearTransactionHistory() {
        transaction {
            // Delete all incomes and expenses associated with this user
            Incomes.deleteWhere { user eq this@User.id }
            Expenses.deleteWhere { user eq this@User.id }
        }
    }

    // Authentication functions
    companion object : IntEntityClass<User>(Users) {
        fun register(name: String, username: String, email: String, password: String): User? {
            return transaction {
                if (User.find { Users.username eq username }.empty() && User.find { Users.email eq email }.empty()) {
                    User.new {
                        this.name = name
                        this.username = username
                        this.email = email
                        this.password = BCrypt.hashpw(password, BCrypt.gensalt())
                    }
                } else {
                    null // Username or email already exists
                }
            }
        }

        fun login(identifier: String, password: String): User? {
            return transaction {
                val user = User.find { (Users.username eq identifier) or (Users.email eq identifier) }.singleOrNull()
                if (user != null && BCrypt.checkpw(password, user.password)) {
                    user
                } else {
                    null // Invalid username/email or password
                }
            }
        }

        fun deleteAccount(userId: Int) {
            transaction {
                User.findById(userId)?.delete()
            }
        }
    }
}


class IncomeHead(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<IncomeHead>(IncomeHeads)

    var user by User referencedOn IncomeHeads.user
    var name by IncomeHeads.name
    var frequency by IncomeHeads.frequency
}

class Income(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Income>(Incomes)

    var user by User referencedOn Incomes.user
    var date by Incomes.date
    var head by IncomeHead referencedOn Incomes.head
    var amount by Incomes.amount
    var description by Incomes.description
}

class ExpenseHead(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ExpenseHead>(ExpenseHeads)

    var user by User referencedOn ExpenseHeads.user
    var name by ExpenseHeads.name
    var frequency by ExpenseHeads.frequency
}

class Expense(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Expense>(Expenses)

    var user by User referencedOn Expenses.user
    var date by Expenses.date
    var head by ExpenseHead referencedOn Expenses.head
    var amount by Expenses.amount
    var description by Expenses.description
}
