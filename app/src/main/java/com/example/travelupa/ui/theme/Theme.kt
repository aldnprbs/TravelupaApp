package com.example.travelupa.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Definisikan skema warna gelap
private val DarkColorScheme = darkColorScheme(
    primary = CyanAqua,
    background = DarkBlue,
    surface = SpaceCadet,
    onPrimary = DarkBlue,
    onBackground = Platinum,
    onSurface = Platinum
)

@Composable
fun TravelupaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), // Selalu gunakan tema gelap untuk contoh ini
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window

            // --- PERUBAHAN DI SINI ---
            // Kode lama yang deprecated (usang) dihapus:
            // window.statusBarColor = Color.Transparent.toArgb()

            // Menggunakan cara modern untuk membuat UI menjadi edge-to-edge (sampai ke tepi layar)
            WindowCompat.setDecorFitsSystemWindows(window, false)

            // Mengatur warna ikon di status bar (misal: jam, sinyal) agar kontras
            val insetsController = WindowCompat.getInsetsController(window, view)
            insetsController.isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}