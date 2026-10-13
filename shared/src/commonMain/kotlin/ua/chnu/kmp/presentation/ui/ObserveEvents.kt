@file:Suppress("NOTHING_TO_INLINE")
package ua.chnu.kmp.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

@Composable
inline fun <T> ObserveEvents(flow: Flow<T>, noinline onEvent: (T) -> Unit) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val collector by rememberUpdatedState(newValue = onEvent)
    LaunchedEffect(flow) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) {
                flow.collect(collector)
            }
        }
    }
}
