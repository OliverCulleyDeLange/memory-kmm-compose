package uk.co.oliverdelange.memory.icon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Cross: ImageVector
    get() {
        if (_Cross != null) {
            return _Cross!!
        }
        _Cross = ImageVector.Builder(
            name = "Cross2",
            defaultWidth = 15.dp,
            defaultHeight = 15.dp,
            viewportWidth = 15f,
            viewportHeight = 15f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(11.7816f, 4.03157f)
                curveTo(12.0062f, 3.807f, 12.0062f, 3.443f, 11.7816f, 3.2184f)
                curveTo(11.5571f, 2.9939f, 11.193f, 2.9939f, 10.9685f, 3.2184f)
                lineTo(7.50005f, 6.68682f)
                lineTo(4.03164f, 3.2184f)
                curveTo(3.8071f, 2.9939f, 3.443f, 2.9939f, 3.2185f, 3.2184f)
                curveTo(2.9939f, 3.443f, 2.9939f, 3.807f, 3.2185f, 4.0316f)
                lineTo(6.68688f, 7.49999f)
                lineTo(3.21846f, 10.9684f)
                curveTo(2.9939f, 11.193f, 2.9939f, 11.557f, 3.2185f, 11.7816f)
                curveTo(3.443f, 12.0061f, 3.8071f, 12.0061f, 4.0316f, 11.7816f)
                lineTo(7.50005f, 8.31316f)
                lineTo(10.9685f, 11.7816f)
                curveTo(11.193f, 12.0061f, 11.5571f, 12.0061f, 11.7816f, 11.7816f)
                curveTo(12.0062f, 11.557f, 12.0062f, 11.193f, 11.7816f, 10.9684f)
                lineTo(8.31322f, 7.49999f)
                lineTo(11.7816f, 4.03157f)
                close()
            }
        }.build()
        return _Cross!!
    }

private var _Cross: ImageVector? = null
