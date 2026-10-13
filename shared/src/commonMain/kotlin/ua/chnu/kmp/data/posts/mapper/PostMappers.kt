package ua.chnu.kmp.data.posts.mapper

import ua.chnu.kmp.data.posts.model.requests.NewPost as NewPostDto
import ua.chnu.kmp.data.posts.model.responses.DeletedPost as DeletedPostDto
import ua.chnu.kmp.data.posts.model.responses.Post as PostDto
import ua.chnu.kmp.data.posts.model.responses.Reactions
import ua.chnu.kmp.domain.posts.model.DeletedPost
import ua.chnu.kmp.domain.posts.model.NewPost
import ua.chnu.kmp.domain.posts.model.Post

internal fun PostDto.toDomain(): Post = Post(
    id = id,
    userId = userId,
    title = title,
    body = body,
    tags = tags,
    likes = reactions.likes,
    dislikes = reactions.dislikes,
    views = views,
)

internal fun DeletedPostDto.toDomain(): DeletedPost = DeletedPost(
    post = Post(
        id = id,
        userId = userId,
        title = title,
        body = body,
        tags = tags,
        likes = reactions.likes,
        dislikes = reactions.dislikes,
        views = views,
    ),
    deletedOn = deletedOn,
)

internal fun Post.toDto(): PostDto = PostDto(
    body = body,
    id = id,
    reactions = Reactions(dislikes = dislikes, likes = likes),
    tags = tags,
    title = title,
    userId = userId,
    views = views,
)

internal fun NewPost.toDto(): NewPostDto = NewPostDto(
    body = body,
    reactions = Reactions(),
    tags = tags,
    title = title,
    userId = userId,
)
