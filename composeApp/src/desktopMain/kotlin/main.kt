
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import pettycashdesktop.composeapp.generated.resources.Res
import pettycashdesktop.composeapp.generated.resources.notebook

@OptIn(ExperimentalResourceApi::class)
fun main() = application {
    val state = rememberWindowState(
        placement = WindowPlacement.Maximized,
        size = DpSize(1280.dp, 720.dp)
    )

    Window(
        onCloseRequest = ::exitApplication,
        title = "Petty Cashbook",
        icon =  painterResource(Res.drawable.notebook),
        state = state
    ) {
        App()
    }
}