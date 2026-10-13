package ua.chnu.kmp.data.posts

import io.ktor.client.HttpClient
import io.ktor.client.request.accept
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import ua.chnu.kmp.data.common.NetworkResult
import ua.chnu.kmp.data.common.safeRequest
import ua.chnu.kmp.data.posts.model.requests.NewPost
import ua.chnu.kmp.data.posts.model.responses.DeletedPost
import ua.chnu.kmp.data.posts.model.responses.Post
import ua.chnu.kmp.data.posts.model.responses.Posts

internal class AppPostApiService (
    private val client: HttpClient
) : PostApiService {

    override suspend fun getAllPosts(): NetworkResult<Posts> {
        return client.safeRequest {
            get("$BASE_URL$POSTS_API") {
                accept(ContentType.Application.Json)
            }
        }
    }

    override suspend fun addPost(post: NewPost): NetworkResult<Post> {
        return client.safeRequest {
            post("$BASE_URL$POSTS_API/$ADD_POST") {
                contentType(ContentType.Application.Json)
                setBody(post)
            }
        }
    }

    override suspend fun updatePost(post: Post): NetworkResult<Post> {
        return client.safeRequest {
            put("$BASE_URL$POSTS_API/${post.id}") {
                contentType(ContentType.Application.Json)
                setBody(post)
            }
        }
    }

    override suspend fun deletePost(postId: Int): NetworkResult<DeletedPost> {
        return client.safeRequest {
            delete("$BASE_URL$POSTS_API/$postId") {
                accept(ContentType.Application.Json)
            }
        }
    }
}
