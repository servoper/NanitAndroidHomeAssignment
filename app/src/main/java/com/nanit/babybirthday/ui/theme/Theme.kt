package com.nanit.babybirthday.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.nanit.babybirthday.R

private val DarkColorScheme = darkColorScheme(
    primary = Purple80, secondary = PurpleGrey80, tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40, secondary = PurpleGrey40, tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun NanitBabyBirthdayTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true, content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme, typography = Typography, content = content
    )
}

interface NanitTheme {
    fun getBackgroundColor(): Color
    fun getOverlayResource(): Int
    fun getBabyImageBorderColor(): Color
    fun getBabyImagePlaceholderResource(): Int
    fun getAddImageResource(): Int
}

class NanitBlueTheme : NanitTheme {
    override fun getBackgroundColor(): Color = Blue20

    override fun getOverlayResource(): Int = R.drawable.bg_blue

    override fun getBabyImageBorderColor(): Color = Blue40

    override fun getBabyImagePlaceholderResource(): Int = R.drawable.image_paceholder_blue

    override fun getAddImageResource(): Int = R.drawable.add_image_blue
}

class NanitYellowTheme : NanitTheme {
    override fun getBackgroundColor(): Color = Yellow20

    override fun getOverlayResource(): Int = R.drawable.bg_yellow

    override fun getBabyImageBorderColor(): Color = Yellow40

    override fun getBabyImagePlaceholderResource(): Int = R.drawable.image_paceholder_yellow

    override fun getAddImageResource(): Int = R.drawable.add_image_yellow
}

class NanitGreenTheme : NanitTheme {
    override fun getBackgroundColor(): Color = Green20

    override fun getOverlayResource(): Int = R.drawable.bg_green

    override fun getBabyImageBorderColor(): Color = Green40

    override fun getBabyImagePlaceholderResource(): Int = R.drawable.image_paceholder_green

    override fun getAddImageResource(): Int = R.drawable.add_image_green
}

