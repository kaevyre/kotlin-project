package ua.chnu.kmp

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import ua.chnu.kmp.di.initKoin
import ua.chnu.kmp.presentation.App

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    initKoin { printLogger() }
    ComposeViewport {
        App()
    }
}