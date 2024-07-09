package populate

import framework.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils.create
import org.jetbrains.exposed.sql.transactions.transaction
import org.mindrot.jbcrypt.BCrypt
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate
import kotlin.random.Random

private fun populateDatabase() = transaction {
    // Add users
    val users = listOf(
        User.new {
            name = "Harshith Doddipalli"
            username = "hatitu"
            email = "harshith.doddipalli@outlook.com"
            password = BCrypt.hashpw("Yachi123", BCrypt.gensalt())
        },
        User.new {
            name = "B. Yakshika Goud"
            username = "yachika"
            email = "yakshikagoud2006@gmail.com"
            password = BCrypt.hashpw("Hachi123", BCrypt.gensalt())
        },
        User.new {
            name = "Surya Narayana"
            username = "surya"
            email = "suryaptc@iare.ac.in"
            password = BCrypt.hashpw("surya123", BCrypt.gensalt())
        },
        User.new {
            name = "Sri Laasya"
            username = "laasya"
            email = "23951a0546@iare.ac.in"
            password = BCrypt.hashpw("laasya123", BCrypt.gensalt())
        }
    )

    val incomeHeads = listOf("Salary", "Freelancing", "Investment", "Gift")
    val expenseHeads = listOf("Groceries", "Rent", "Utilities", "Entertainment")

    // Function to generate random date within a year
    fun randomDate(): LocalDate {
        val start = LocalDate.now().minusYears(1)
        val end = LocalDate.now()
        return start.plusDays(Random.nextLong(end.toEpochDay() - start.toEpochDay() + 1))
    }

    // Function to generate random amount
    fun randomAmount(): BigDecimal {
        return BigDecimal(Random.nextDouble(10.0, 1000.0)).setScale(2, RoundingMode.HALF_UP)
    }

    // Add random income and expense heads and transactions for each user
    users.forEach { user ->
        val userIncomeHeads = incomeHeads.map { head ->
            IncomeHead.new {
                this.user = user
                name = head
                frequency = if (head == "Salary") "Monthly" else "Ad hoc"
            }
        }

        val userExpenseHeads = expenseHeads.map { head ->
            ExpenseHead.new {
                this.user = user
                name = head
                frequency = if (head == "Rent") "Monthly" else "Ad hoc"
            }
        }

        repeat(20) {
            Income.new {
                this.user = user
                date = randomDate()
                head = userIncomeHeads.random()
                amount = randomAmount()
                description = "Random income description"
            }

            Expense.new {
                this.user = user
                date = randomDate()
                head = userExpenseHeads.random()
                amount = randomAmount()
                description = "Random expense description"
            }
        }
    }

    println("Database populated with random data.")
}

// Call the framework.populateDatabase function in your framework.main function or wherever appropriate
fun main() {
    Database.connect("jdbc:sqlite:C:\\Users\\Harshith\\IdeaProjects\\PettyCash Desktop\\composeApp\\src\\commonMain\\composeResources\\files\\test.db", driver = "org.sqlite.JDBC")

    transaction {
        create(Users, IncomeHeads, Incomes, ExpenseHeads, Expenses)
    }

    populateDatabase()
}
