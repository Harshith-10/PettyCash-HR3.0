package ui.pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import pettycashdesktop.composeapp.generated.resources.Res
import pettycashdesktop.composeapp.generated.resources.downward_24
import pettycashdesktop.composeapp.generated.resources.equal_24
import pettycashdesktop.composeapp.generated.resources.upward_24
import ui.theme.SublimeLight
import viewmodel.PwettyViewModel

@Composable
fun DashboardContent(
    pwettyViewModel: PwettyViewModel
) {
    val dashboardStats by pwettyViewModel.dashboardStats.collectAsState()
    val balanceChange by pwettyViewModel.monthlyChange.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
            .background(SublimeLight)
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            FinanceCard(
                title = "Current Balance",
                content = "$${dashboardStats?.currentBalance ?: 0}",
                change = balanceChange?.first?.toInt() ?: 0
            )

            Spacer(modifier = Modifier.width(8.dp))

            FinanceCard(
                title = "Total Income",
                content = "$${dashboardStats?.totalIncome ?: 0}",
                change = balanceChange?.second?.toInt() ?: 0
            )

            Spacer(modifier = Modifier.width(8.dp))

            FinanceCard(
                title = "Total Expenses",
                content = "$${dashboardStats?.totalExpenses ?: 0}",
                change = balanceChange?.third?.toInt() ?: 0
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "No Graph Data",
                fontSize = 32.sp
            )
        }
    }
}

@Composable
private fun RowScope.FinanceCard(
    title: String,
    content: String,
    change: Int,
    subscript: String = "vs last month"
) {
    Column(
        modifier = Modifier.weight(1f)
            .height(160.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(
                border = BorderStroke(1.dp, Color.LightGray),
                shape = RoundedCornerShape(12.dp)
            )
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            fontSize = 14.sp,
            color = Color.Gray
        )
        // Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = content,
            fontSize = 24.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .border(
                        border = BorderStroke(
                            1.dp,
                            when {
                                change > 0 -> Color(0xFF45A247)
                                change < 0 -> Color.Red
                                else -> Color.Gray
                            }
                        ),
                        shape = CircleShape
                    )
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(
                        when {
                            change > 0 -> Res.drawable.upward_24
                            change < 0 -> Res.drawable.downward_24
                            else -> Res.drawable.equal_24
                        }
                    ),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = when {
                        change > 0 -> Color(0xFF45A247)
                        change < 0 -> Color.Red
                        else -> Color.Gray
                    }
                )

                Text(
                    text = "$change%",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = when {
                        change > 0 -> Color(0xFF45A247)
                        change < 0 -> Color.Red
                        else -> Color.Gray
                    },
                    modifier = Modifier
                        .padding(start = 4.dp, end = 4.dp, bottom = 4.dp)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = subscript,
                color = Color.Gray,
                fontSize = 14.sp
            )
        }
    }
}