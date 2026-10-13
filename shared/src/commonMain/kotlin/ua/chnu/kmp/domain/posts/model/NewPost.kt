package ua.chnu.kmp.domain.posts.model

internal data class NewPost(
    val userId: Int,
    val title: String,
    val body: String,
    val tags: List<String> = emptyList(),
)
