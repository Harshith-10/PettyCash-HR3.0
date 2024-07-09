
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.font.FontFamily
import androidx.lifecycle.viewmodel.compose.viewModel
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.ui.tooling.preview.Preview
import pettycashdesktop.composeapp.generated.resources.Res
import pettycashdesktop.composeapp.generated.resources.playwrite
import ui.DashboardUI
import ui.LoginUI
import ui.theme.PrettyTheme
import viewmodel.PwettyViewModel

@Composable
@Preview
fun App() {
    PrettyTheme {
        val logoFontFamily = FontFamily(Font(Res.font.playwrite))
        val pettyModel = viewModel { PwettyViewModel() }

        val loggedInUser by pettyModel.loggedInUser.collectAsState()

        AnimatedContent(
            targetState = loggedInUser != null,
            transitionSpec = {
                fadeIn(animationSpec = tween(300))
                    .togetherWith(fadeOut(animationSpec = tween(300)))
            }
        ) {
            if (it) {
                DashboardUI(logoFontFamily, pettyModel)
            } else {
                LoginUI(logoFontFamily, pettyModel)
            }
        }
    }
}