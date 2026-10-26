package id.aguskurniawan.jobhunter.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFF0B5FA5),
    secondary = Color(0xFF0E7C5B),
    tertiary = Color(0xFFB8860B)
)
private val DarkColors = darkColorScheme(
    primary = Color(0xFF7FB8E8),
    secondary = Color(0xFF4CAF82),
    tertiary = Color(0xFFD4AF37)
)

@Composable
fun JobHunterTheme(darkTheme: Boolean = false, content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        content = content
    )
}
