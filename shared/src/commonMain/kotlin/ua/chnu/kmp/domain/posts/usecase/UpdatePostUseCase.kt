package ua.chnu.kmp.domain.posts.usecase

import ua.chnu.kmp.data.common.NetworkResult
import ua.chnu.kmp.domain.posts.PostRepository
import ua.chnu.kmp.domain.posts.model.Post

internal class UpdatePostUseCase(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(post: Post): NetworkResult<Post> = postRepository.updatePost(post)
}
