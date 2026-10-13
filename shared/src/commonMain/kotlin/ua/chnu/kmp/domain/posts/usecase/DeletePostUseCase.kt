package ua.chnu.kmp.domain.posts.usecase

import ua.chnu.kmp.data.common.NetworkResult
import ua.chnu.kmp.domain.posts.PostRepository
import ua.chnu.kmp.domain.posts.model.DeletedPost

internal class DeletePostUseCase(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(postId: Int): NetworkResult<DeletedPost> = postRepository.deletePost(postId)
}
