package ua.chnu.kmp.data.posts

import ua.chnu.kmp.data.common.NetworkResult
import ua.chnu.kmp.data.common.map
import ua.chnu.kmp.data.posts.mapper.toDomain
import ua.chnu.kmp.data.posts.mapper.toDto
import ua.chnu.kmp.domain.posts.PostRepository
import ua.chnu.kmp.domain.posts.model.DeletedPost
import ua.chnu.kmp.domain.posts.model.NewPost
import ua.chnu.kmp.domain.posts.model.Post

internal class AppPostRepository(
    private val postApiService: PostApiService
) : PostRepository {

    override suspend fun getAllPosts(): NetworkResult<List<Post>> {
        return postApiService.getAllPosts().map { posts -> posts.posts.map { it.toDomain() } }
    }

    override suspend fun addPost(post: NewPost): NetworkResult<Post> {
        return postApiService.addPost(post.toDto()).map { it.toDomain() }
    }

    override suspend fun updatePost(post: Post): NetworkResult<Post> {
        return postApiService.updatePost(post.toDto()).map { it.toDomain() }
    }

    override suspend fun deletePost(postId: Int): NetworkResult<DeletedPost> {
        return postApiService.deletePost(postId).map { it.toDomain() }
    }
}
