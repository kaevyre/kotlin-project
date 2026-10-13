package ua.chnu.kmp.presentation

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ua.chnu.kmp.data.common.NetworkResult
import ua.chnu.kmp.domain.posts.model.NewPost
import ua.chnu.kmp.domain.posts.usecase.CreatePostUseCase
import ua.chnu.kmp.domain.posts.usecase.DeletePostUseCase
import ua.chnu.kmp.domain.posts.usecase.GetPostsUseCase
import ua.chnu.kmp.domain.posts.usecase.UpdatePostUseCase
import kotlin.time.Duration.Companion.milliseconds

@Stable
class AppViewModel internal constructor(
    private val getPostsUseCase: GetPostsUseCase,
    private val createPostUseCase: CreatePostUseCase,
    private val updatePostUseCase: UpdatePostUseCase,
    private val deletePostUseCase: DeletePostUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(AppState())
    internal val state: StateFlow<AppState> = _state.asStateFlow()

    private val _events = Channel<AppEvent>(capacity = Channel.BUFFERED)
    val events: Flow<AppEvent> = _events.receiveAsFlow()

    init {
        fetchPosts()
    }

    fun onAction(action: AppAction) {
        when (action) {
            AppAction.OnFetchPosts -> fetchPosts()
            AppAction.OnCreatePost -> createPost()
            AppAction.OnUpdatePost -> updatePost()
            AppAction.OnDeletePost -> deletePost()
        }
    }

    private fun fetchPosts() {
        viewModelScope.launch {
            startOperation()
            delay(350.milliseconds)
            when (val result = getPostsUseCase()) {
                is NetworkResult.Success -> _state.update {
                    it.copy(
                        posts = result.data,
                        result = result.data.toString(),
                    )
                }
                is NetworkResult.Failure -> _events.trySend(AppEvent.ShowGetErrorSnackbar(result.errorMessage))
            }
            finishOperation()
        }
    }

    private fun createPost() {
        viewModelScope.launch {
            startOperation()
            when (val result = createPostUseCase(createNewPost())) {
                is NetworkResult.Success -> _state.update { it.copy(result = result.data.toString()) }
                is NetworkResult.Failure -> _events.trySend(AppEvent.ShowPostErrorSnackbar(result.errorMessage))
            }
            finishOperation()
        }
    }

    private fun updatePost() {
        viewModelScope.launch {
            startOperation()
            delay(350.milliseconds)
            val first = _state.value.posts.firstOrNull()
            if (first == null) {
                _events.trySend(AppEvent.ShowPutErrorSnackbar(NO_POSTS_MESSAGE))
            } else {
                when (val result = updatePostUseCase(first.copy(body = "Updated body"))) {
                    is NetworkResult.Success -> _state.update { it.copy(result = result.data.toString()) }
                    is NetworkResult.Failure -> _events.trySend(AppEvent.ShowPutErrorSnackbar(result.errorMessage))
                }
            }
            finishOperation()
        }
    }

    private fun deletePost() {
        viewModelScope.launch {
            startOperation()
            delay(350.milliseconds)
            val first = _state.value.posts.firstOrNull()
            if (first == null) {
                _events.trySend(AppEvent.ShowDeleteErrorSnackbar(NO_POSTS_MESSAGE))
            } else {
                when (val result = deletePostUseCase(first.id)) {
                    is NetworkResult.Success -> _state.update { it.copy(result = result.data.toString()) }
                    is NetworkResult.Failure -> _state.update { it.copy(error = result.errorMessage) }
                }
            }
            finishOperation()
        }
    }

    private fun startOperation() {
        _state.update { it.copy(isProgressVisible = true, result = null, error = null) }
    }

    private fun finishOperation() {
        _state.update { it.copy(isProgressVisible = false) }
    }

    private fun createNewPost() = NewPost(
        userId = 5,
        title = "Title text",
        body = "Body text",
        tags = listOf("Tag 1", "Tag 2"),
    )

    private companion object {
        const val NO_POSTS_MESSAGE = "No posts loaded. Press GET first."
    }
}
