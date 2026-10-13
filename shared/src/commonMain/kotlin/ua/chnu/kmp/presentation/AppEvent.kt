package ua.chnu.kmp.presentation

sealed class AppEvent {
    data class ShowGetErrorSnackbar(val errorMessage: String) : AppEvent()
    data class ShowPostErrorSnackbar(val errorMessage: String) : AppEvent()
    data class ShowPutErrorSnackbar(val errorMessage: String) : AppEvent()
    data class ShowDeleteErrorSnackbar(val errorMessage: String) : AppEvent()
}
