package ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val GoogleIcon: ImageVector
    get() {
        if (mGoogle != null) {
            return mGoogle!!
        }
        mGoogle = Builder(name = "Google", defaultWidth = 800.0.dp, defaultHeight = 800.0.dp,
                viewportWidth = 32.0f, viewportHeight = 32.0f).apply {
            path(fill = SolidColor(Color(0xFF00ac47)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(23.75f, 16.0f)
                arcTo(7.7446f, 7.7446f, 0.0f, isMoreThanHalf = false, isPositiveArc = true, x1 = 8.7177f, y1 = 18.6259f)
                lineTo(4.2849f, 22.1721f)
                arcTo(13.244f, 13.244f, 0.0f, isMoreThanHalf = false, isPositiveArc = false, x1 = 29.25f, y1 = 16.0f)
            }
            path(fill = SolidColor(Color(0xFF4285f4)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(23.75f, 16.0f)
                arcToRelative(7.7387f, 7.7387f, 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = -3.2516f,
                    dy1 = 6.2987f
                )
                lineToRelative(4.3824f, 3.5059f)
                arcTo(13.2042f, 13.2042f, 0.0f, isMoreThanHalf = false, isPositiveArc = false, x1 = 29.25f, y1 = 16.0f)
            }
            path(fill = SolidColor(Color(0xFFffba00)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(8.25f, 16.0f)
                arcToRelative(7.698f, 7.698f, 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 0.4677f,
                    dy1 = -2.6259f
                )
                lineTo(4.2849f, 9.8279f)
                arcToRelative(13.177f, 13.177f, 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    dx1 = 0.0f,
                    dy1 = 12.3442f
                )
                lineToRelative(4.4328f, -3.5462f)
                arcTo(7.698f, 7.698f, 0.0f, isMoreThanHalf = false, isPositiveArc = true, x1 = 8.25f, y1 = 16.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFF2ab2db)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(8.718f, 13.374f)
                lineToRelative(0.0f, 0.0f)
                lineToRelative(0.0f, 0.0f)
                lineToRelative(0.0f, 0.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFFea4435)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(16.0f, 8.25f)
                arcToRelative(7.699f, 7.699f, 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    dx1 = 4.558f,
                    dy1 = 1.4958f
                )
                lineToRelative(4.06f, -3.7893f)
                arcTo(13.2152f, 13.2152f, 0.0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    x1 = 4.2849f,
                    y1 = 9.8279f
                )
                lineToRelative(4.4328f, 3.5462f)
                arcTo(7.756f, 7.756f, 0.0f, isMoreThanHalf = false, isPositiveArc = true, x1 = 16.0f, y1 = 8.25f)
                close()
            }
            path(fill = SolidColor(Color(0xFF2ab2db)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(8.718f, 18.626f)
                lineToRelative(0.0f, 0.0f)
                lineToRelative(0.0f, 0.0f)
                lineToRelative(0.0f, 0.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFF4285f4)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(29.25f, 15.0f)
                verticalLineToRelative(1.0f)
                lineTo(27.0f, 19.5f)
                horizontalLineTo(16.5f)
                verticalLineTo(14.0f)
                horizontalLineTo(28.25f)
                arcTo(1.0f, 1.0f, 0.0f, isMoreThanHalf = false, isPositiveArc = true, x1 = 29.25f, y1 = 15.0f)
                close()
            }
        }
        .build()
        return mGoogle!!
    }

private var mGoogle: ImageVector? = null
