package ui

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import pettycashdesktop.composeapp.generated.resources.*
import ui.components.PlainTextField
import ui.pages.*
import viewmodel.PwettyViewModel
import viewmodel.Screen

val backgroundColor = Color.LightGray.copy(alpha = 0.75f)

@Composable
fun DashboardUI(
    logoFontFamily: FontFamily,
    viewModel: PwettyViewModel
) {
    val loggedInUser by viewModel.loggedInUser.collectAsState()

    Row(
        modifier = Modifier.fillMaxSize()
            .background(Color.White)
    ) {
        val selectedScreen by viewModel.selectedScreen.collectAsState()

        Column(
            modifier = Modifier.fillMaxHeight()
                .fillMaxWidth(1f/4f)
                .padding(16.dp)
        ) {
            Text(
                text = "Petty Cashbook",
                modifier = Modifier.padding(4.dp),
                style = MaterialTheme.typography.h4.copy(
                    fontFamily = logoFontFamily,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier.clip(RoundedCornerShape(16.dp))
                    .background(backgroundColor)
                    .padding(16.dp)
            ) {
                MenuItem(viewModel, Screen.Dashboard, Res.drawable.dashboard_24, "Dashboard")
                Spacer(modifier = Modifier.height(8.dp))
                MenuItem(viewModel, Screen.Transactions, Res.drawable.payments_24, "Transactions")
                Spacer(modifier = Modifier.height(8.dp))
                MenuItem(viewModel, Screen.MyWallet, Res.drawable.wallet_24, "My Wallet")
                Spacer(modifier = Modifier.height(8.dp))
                MenuItem(viewModel, Screen.Stats, Res.drawable.monitoring_24, "Stats")
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth()
                    .clip(CircleShape)
                    .clickable {
                        viewModel.selectScreen(Screen.Settings)
                    }
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(Res.drawable.settings_24),
                    contentDescription = "Settings",
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Settings",
                    modifier = Modifier.padding(4.dp),
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth()
                    .clip(CircleShape)
                    .clickable {
                        viewModel.logout()
                    }
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(Res.drawable.logout_24),
                    contentDescription = "Logout",
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Logout",
                    modifier = Modifier.padding(4.dp),
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Column {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .background(Color.White)
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                var searchQuery by remember { mutableStateOf("") }

                PlainTextField(
                    value = searchQuery,
                    onValueChange = {
                        searchQuery = it
                        // TODO: viewModel.filterTransactions(it)
                    },
                    modifier = Modifier.fillMaxWidth(0.5f)
                        .border(
                            border = BorderStroke(1.dp, Color.Gray),
                            shape = CircleShape
                        )
                        .padding(8.dp),
                    label = {
                        Text(
                            text = AnnotatedString("Search"),
                            style = MaterialTheme.typography.body1.copy(color = Color.Gray),
                        )
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(Res.drawable.search_24),
                            contentDescription = null,
                            tint = Color.DarkGray,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                )

                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    painter = painterResource(Res.drawable.mail_24),
                    contentDescription = null,
                    tint = Color.DarkGray,
                    modifier = Modifier.size(20.dp).clip(CircleShape)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Icon(
                    painter = painterResource(Res.drawable.notifications_24),
                    contentDescription = null,
                    tint = Color.DarkGray,
                    modifier = Modifier.size(20.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Row(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(backgroundColor)
                        .padding(start = 8.dp, end = 16.dp, top = 4.dp, bottom = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(Res.drawable.face_24),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        colorFilter = ColorFilter.tint(Color.DarkGray),
                        modifier = Modifier.size(20.dp).clip(CircleShape)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = loggedInUser?.name ?: "Error",
                        color = Color.DarkGray,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }
            }

            AnimatedContent(
                targetState = selectedScreen,
                modifier = Modifier.fillMaxSize()
                    .background(Color.White)
                    .clip(RoundedCornerShape(topStart = 20.dp)),
                transitionSpec = {
                    fadeIn(
                        tween(300)
                    ).togetherWith(
                        fadeOut(tween(300))
                    )
                },
            ) {
                when (selectedScreen) {
                    Screen.Dashboard -> DashboardContent(viewModel)
                    Screen.Transactions -> TransactionsContent()
                    Screen.MyWallet -> MyWalletContent()
                    Screen.Stats -> StatsContent()
                    Screen.Settings -> SettingsContent()
                }
            }
        }
    }
}

@Composable
private fun MenuItem(
    viewModel: PwettyViewModel,
    screen: Screen,
    icon: DrawableResource,
    text: String
) {
    val selectedScreen by viewModel.selectedScreen.collectAsState()

    val isSelected = selectedScreen == screen

    val backgroundColor by animateColorAsState(targetValue = if (isSelected) Color.Black else Color.White)
    val contentColor by animateColorAsState(targetValue = if (isSelected) Color.White else Color.Black)

    Row(
        modifier = Modifier.fillMaxWidth()
            .clip(CircleShape)
            .background(color = backgroundColor)
            .clickable {
                viewModel.selectScreen(screen)
            }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = text,
            tint = contentColor,
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, color = contentColor)
    }
}