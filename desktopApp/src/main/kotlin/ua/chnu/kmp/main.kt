package ua.chnu.kmp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ua.chnu.kmp.di.initKoin
import ua.chnu.kmp.presentation.App

fun main() = application {
    initKoin { printLogger() }
    Window(
        onCloseRequest = ::exitApplication,
        title = "AdvancedKotlinMultiplatform",
    ) {
        App()
    }
}
