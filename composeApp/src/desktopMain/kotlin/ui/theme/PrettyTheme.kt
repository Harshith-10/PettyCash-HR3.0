package ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import org.jetbrains.compose.resources.Font
import pettycashdesktop.composeapp.generated.resources.Res
import pettycashdesktop.composeapp.generated.resources.inter

@Composable
fun PrettyTheme(
    content: @Composable () -> Unit
) {
    val Inter = FontFamily(Font(Res.font.inter))
    MaterialTheme(
        typography = MaterialTheme.typography.copy(
            h1 = MaterialTheme.typography.h1.copy(
                fontFamily = Inter
            ),
            h2 = MaterialTheme.typography.h2.copy(
                fontFamily = Inter
            ),
            h3 = MaterialTheme.typography.h3.copy(
                fontFamily = Inter
            ),
            h4 = MaterialTheme.typography.h4.copy(
                fontFamily = Inter
            ),
            h5 = MaterialTheme.typography.h5.copy(
                fontFamily = Inter
            ),
            h6 = MaterialTheme.typography.h6.copy(
                fontFamily = Inter
            ),
            subtitle1 = MaterialTheme.typography.subtitle1.copy(
                fontFamily = Inter
            ),
            subtitle2 = MaterialTheme.typography.subtitle2.copy(
                fontFamily = Inter
            ),
            body1 = MaterialTheme.typography.body1.copy(
                fontFamily = Inter
            ),
            body2 = MaterialTheme.typography.body2.copy(
                fontFamily = Inter
            ),
            button = MaterialTheme.typography.button.copy(
                fontFamily = Inter
            ),
            caption = MaterialTheme.typography.caption.copy(
                fontFamily = Inter
            ),
            overline = MaterialTheme.typography.overline.copy(
                fontFamily = Inter
            )
        ),
        content = content
    )
}