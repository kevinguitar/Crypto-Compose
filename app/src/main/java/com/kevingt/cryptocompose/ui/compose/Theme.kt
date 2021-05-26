package com.kevingt.cryptocompose.ui.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val DarkColorPalette = darkColors(

)

private val LightColorPalette = lightColors(

)

private val LightTypography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        color = Color.Black
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        color = Color.Black
    )
)

private val DarkTypography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        color = Color.White
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        color = Color.White
    )
)

@Composable
fun ComposeCryptoTheme(
    isDarkTheme: State<Boolean>,
    content: @Composable () -> Unit
) {
    val colors = if (isDarkTheme.value) DarkColorPalette else LightColorPalette
    val typography = if (isDarkTheme.value) DarkTypography else LightTypography

    MaterialTheme(
        colors = colors,
        typography = typography,
        content = {
            Surface(
                color = MaterialTheme.colors.background,
                modifier = Modifier.fillMaxSize(),
                content = content
            )
        }
    )
}