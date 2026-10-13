package ua.chnu.kmp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import ua.chnu.kmp.presentation.ui.ObserveEvents

@Composable
fun App(
    viewModel: AppViewModel = koinViewModel(),
) {
    MaterialTheme {

        val snackbarHostState = remember { SnackbarHostState() }
        val coroutineScope = rememberCoroutineScope()

        val state by viewModel.state.collectAsStateWithLifecycle()

        ObserveEvents(viewModel.events) { event ->
            when (event) {
                is AppEvent.ShowDeleteErrorSnackbar -> {
                    showSnackbar(coroutineScope, snackbarHostState, event.errorMessage)
                }
                is AppEvent.ShowGetErrorSnackbar -> {
                    showSnackbar(coroutineScope, snackbarHostState, event.errorMessage)
                }
                is AppEvent.ShowPostErrorSnackbar -> {
                    showSnackbar(coroutineScope, snackbarHostState, event.errorMessage)
                }
                is AppEvent.ShowPutErrorSnackbar -> {
                    showSnackbar(coroutineScope, snackbarHostState, event.errorMessage)
                }
            }
        }

        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .systemBarsPadding()
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AppContent(
                state = state,
                onGet = {
                    viewModel.onAction(AppAction.OnFetchPosts)
                },
                onPost = {
                    viewModel.onAction(AppAction.OnCreatePost)
                },
                onPut = {
                    viewModel.onAction(AppAction.OnUpdatePost)
                },
                onDelete = {
                    viewModel.onAction(AppAction.OnDeletePost)
                }
            )
        }
    }
}

private fun showSnackbar(
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    message: String
) {
    scope.launch {
        snackbarHostState.showSnackbar(
            message = message,
            duration = SnackbarDuration.Short
        )
    }
}

@Composable
private fun AppContent(
    state: AppState,
    onGet: () -> Unit,
    onPost: () -> Unit,
    onPut: () -> Unit,
    onDelete: () -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {

            Button(
                modifier = Modifier.weight(1F),
                onClick = { onGet() }
            ) {
                Text("GET")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                modifier = Modifier.weight(1F),
                onClick = { onPost() }
            ) {
                Text("POST")
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {

            Button(
                modifier = Modifier.weight(1F),
                onClick = { onPut() }
            ) {
                Text("PUT")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                modifier = Modifier.weight(1F),
                onClick = { onDelete() }
            ) {
                Text("DELETE")
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        if (state.isProgressVisible) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))
        }

        state.result?.let {
            val scrollState = rememberScrollState()
            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                text = it
            )
        }

        state.error?.let {
            val scrollState = rememberScrollState()
            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Red,
                text = it
            )
        }
    }
}

@Preview
@Composable
private fun AppContentPreview() {
    AppContent(AppState(), {}, {}, {}, {})
}
