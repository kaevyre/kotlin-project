package ua.chnu.kmp.data.posts.model.responses

import kotlinx.serialization.Serializable

@Serializable
internal data class DeletedPost (
    val body: String,
    val id: Int,
    val reactions: Reactions,
    val tags: List<String>,
    val title: String,
    val userId: Int,
    val views: Int = 0,
    val isDeleted: Boolean,
    val deletedOn: String
)