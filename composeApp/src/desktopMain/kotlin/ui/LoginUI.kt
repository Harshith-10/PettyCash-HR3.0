package ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import pettycashdesktop.composeapp.generated.resources.*
import ui.components.PlainTextField
import ui.icons.GoogleIcon
import ui.theme.Lizard
import viewmodel.PwettyViewModel

@Preview
@Composable
fun LoginUI(
    logoFontFamily: FontFamily,
    pettyModel: PwettyViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Lizard),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .width(IntrinsicSize.Max)
                .padding(vertical = 32.dp, horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(16.dp))
                    .background(color = Color.Black.copy(alpha = 0.4f),)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(Res.drawable.notebook),
                    contentDescription = "Logo",
                    modifier = Modifier.size(70.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = "Petty Cashbook",
                        style = MaterialTheme.typography.h5.copy(
                            fontFamily = logoFontFamily,
                            fontWeight = FontWeight.Bold
                        ),
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(1.dp))

                    Text(
                        text = "Your Delightful Money Manager!",
                        style = MaterialTheme.typography.body1.copy(
                            fontStyle = FontStyle.Italic,
                            fontFamily = logoFontFamily,
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape = RoundedCornerShape(16.dp))
                    .background(color = Color.Black.copy(alpha = 0.4f))
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Login",
                    style = MaterialTheme.typography.h4.copy(
                        fontFamily = logoFontFamily,
                        fontWeight = FontWeight.Bold
                    ),
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(12.dp))

                var username by remember { mutableStateOf("") }
                var password by remember { mutableStateOf("") }
                var showPassword by remember { mutableStateOf(false) }
                val focusRequester = remember { FocusRequester() }

                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {
                    PlainTextField(
                        value = username,
                        onValueChange = { username = it },
                        label = {
                            Text(
                                text = "Username",
                                fontSize = 20.sp,
                                color = Color.DarkGray
                            )
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(Res.drawable.face_24),
                                contentDescription = "Username",
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        keyboardActions = KeyboardActions(
                            onDone = { focusRequester.requestFocus() },
                        ),
                        modifier = Modifier
                            .onKeyEvent {
                                if (it.key == Key.Enter && username.isNotEmpty()) {
                                    focusRequester.requestFocus()
                                    true
                                }
                                false
                            }
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                            .fillMaxWidth()
                            .height(56.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        textStyle = TextStyle(
                            fontSize = 20.sp,
                        )
                    )

                    PlainTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = {
                            Text(
                                text = "Password",
                                fontSize = 20.sp,
                                color = Color.DarkGray
                            )
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(Res.drawable.password_24),
                                contentDescription = "Password",
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        trailingIcon = {
                            IconButton(
                                onClick = { showPassword = !showPassword },
                                modifier = Modifier
                                    .pointerHoverIcon(icon = PointerIcon.Hand)
                            ) {
                                Icon(
                                    painter = painterResource(
                                        if (showPassword)
                                            Res.drawable.visibility_off_24
                                        else
                                            Res.drawable.visibility_24
                                    ),
                                    contentDescription = "Visibility",
                                    tint = Color.Black,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        keyboardActions = KeyboardActions(
                            onDone = { pettyModel.login(username, password) },
                        ),
                        modifier = Modifier
                            .focusRequester(focusRequester)
                            .onKeyEvent {
                                if (it.key == Key.Enter) {
                                    pettyModel.login(username, password)
                                    true
                                }
                                false
                            }
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                            .fillMaxWidth()
                            .height(56.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .padding(start = 16.dp, top = 8.dp, bottom = 8.dp, end = 4.dp),
                        textStyle = TextStyle(
                            fontSize = 20.sp,
                        ),
                        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation()
                    )

                    val error by pettyModel.error.collectAsState()

                    AnimatedVisibility(visible = error.isNotEmpty()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(0.7f)
                                .padding(start = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = rememberVectorPainter(Icons.Default.Warning),
                                contentDescription = "Warning",
                                modifier = Modifier.size(12.dp),
                                tint = Color.White
                            )

                            Spacer(modifier = Modifier.width(4.dp))

                            Text(
                                text = error,
                                fontSize = 14.sp,
                                color = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = { pettyModel.login(username, password) },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Black,
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                            .fillMaxWidth()
                            .height(56.dp)
                            .clip(CircleShape)

                    ) {
                        Text(
                            text = "Login",
                            fontSize = 20.sp,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row (
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Divider(
                            color = Color.White,
                            modifier = Modifier
                                .weight(1f)
                                .height(1.dp)
                        )
                        Text(
                            text = "OR",
                            fontSize = 20.sp,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                        Divider(
                            color = Color.White,
                            modifier = Modifier
                                .weight(1f)
                                .height(1.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = { /* TODO */ },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.White,
                            contentColor = Color.Black
                        ),
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                            .fillMaxWidth()
                            .height(56.dp)
                            .clip(CircleShape)

                    ) {
                        Image(
                            painter = rememberVectorPainter(GoogleIcon),
                            contentDescription = "Google",
                            modifier = Modifier.size(24.dp)
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        Text(
                            text = "Sign in with Google",
                            fontSize = 20.sp,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}