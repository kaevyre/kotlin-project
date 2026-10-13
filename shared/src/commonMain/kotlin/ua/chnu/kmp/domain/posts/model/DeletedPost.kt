package ua.chnu.kmp.domain.posts.model

internal data class DeletedPost(
    val post: Post,
    val deletedOn: String,
)
