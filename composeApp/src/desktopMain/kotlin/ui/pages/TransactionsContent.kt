package ui.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


private val headings = listOf(
    "ID", "Date", "To/From", "Amount", "Description", "Type"
)

private val data = listOf(
    listOf("1", "2021-01-01", "Apple, Inc.", "1,00,000", "Salary", "Income"),
    listOf("2", "2021-01-02", "IARE Canteen", "150", "Lunch", "Expense"),
    listOf("3", "2021-02-03", "IARE Canteen", "20", "Coffee", "Expense"),
    listOf("4", "2021-02-04", "KFC", "300", "Dinner", "Expense"),
    listOf("5", "2021-03-05", "IARE Canteen", "70", "Snack", "Expense"),
    listOf("6", "2021-03-06", "KFC", "40", "Drink", "Expense"),
    listOf("7", "2022-04-07", "IARE Canteen", "150", "Lunch", "Expense"),
    listOf("8", "2022-04-08", "KFC", "250", "Dinner", "Expense"),
    listOf("9", "2022-05-09", "IARE Canteen", "60", "Snack", "Expense"),
    listOf("10", "2022-05-10", "KFC", "50", "Drink", "Expense"),
    listOf("11", "2022-06-11", "IARE Canteen", "150", "Lunch", "Expense"),
    listOf("12", "2022-06-12", "KFC", "250", "Dinner", "Expense"),
    listOf("13", "2022-08-13", "IARE Canteen", "80", "Snack", "Expense"),
    listOf("14", "2023-01-14", "KFC", "30", "Drink", "Expense"),
    listOf("15", "2023-02-15", "IARE Canteen", "150", "Lunch", "Expense"),
    listOf("16", "2023-03-16", "KFC", "25", "Dinner", "Expense"),
    listOf("17", "2023-04-17", "IARE Canteen", "10", "Snack", "Expense"),
    listOf("18", "2023-05-18", "KFC", "5", "Drink", "Expense"),
    listOf("19", "2024-06-19", "IARE Canteen", "15", "Lunch", "Expense"),
    listOf("20", "2024-02-20", "KFC", "25", "Dinner", "Expense")
)

@Composable
fun TransactionsContent() {
    Box(
        modifier = Modifier.fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        AllTransactions()
    }
}

@Composable
private fun AllTransactions() {
    Box(
        modifier = Modifier.fillMaxSize()
            .verticalScroll(rememberScrollState())
            .horizontalScroll(rememberScrollState()),
        contentAlignment = Alignment.TopCenter
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            for (i in headings.indices) {
                Text(
                    text = headings[i],
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.background(color = Color.DarkGray)
                        .padding(8.dp)
                        .weight(1f)
                )

                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(IntrinsicSize.Max)
                ) {
                    for (j in data.indices) {
                        Text(
                            text = data[j][i],
                            modifier = Modifier.fillMaxWidth()
                                .background(color = if (j % 2 == 0) Color.LightGray else Color.White)
                                .padding(8.dp)
                                .weight(1f)
                        )
                    }
                }
            }
        }
    }
}