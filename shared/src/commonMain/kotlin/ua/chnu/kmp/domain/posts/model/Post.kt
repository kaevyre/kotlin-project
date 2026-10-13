package ua.chnu.kmp.domain.posts.model

internal data class Post(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String,
    val tags: List<String>,
    val likes: Int,
    val dislikes: Int,
    val views: Int,
)
