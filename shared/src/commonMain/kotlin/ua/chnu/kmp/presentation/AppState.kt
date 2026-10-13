package ua.chnu.kmp.presentation

import androidx.compose.runtime.Immutable
import ua.chnu.kmp.domain.posts.model.Post

@Immutable
internal data class AppState(
    val isProgressVisible: Boolean = false,
    val posts: List<Post> = emptyList(),
    val result: String? = null,
    val error: String? = null,
)
