package ua.chnu.kmp.domain.posts

import ua.chnu.kmp.data.common.NetworkResult
import ua.chnu.kmp.domain.posts.model.DeletedPost
import ua.chnu.kmp.domain.posts.model.NewPost
import ua.chnu.kmp.domain.posts.model.Post

internal interface PostRepository {
    suspend fun getAllPosts(): NetworkResult<List<Post>>
    suspend fun addPost(post: NewPost): NetworkResult<Post>
    suspend fun updatePost(post: Post): NetworkResult<Post>
    suspend fun deletePost(postId: Int): NetworkResult<DeletedPost>
}
