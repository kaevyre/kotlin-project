package ua.chnu.kmp.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.koin.plugin.module.dsl.viewModel
import ua.chnu.kmp.data.posts.AppPostApiService
import ua.chnu.kmp.data.posts.AppPostRepository
import ua.chnu.kmp.data.posts.PostApiService
import ua.chnu.kmp.domain.posts.PostRepository
import ua.chnu.kmp.domain.posts.usecase.CreatePostUseCase
import ua.chnu.kmp.domain.posts.usecase.DeletePostUseCase
import ua.chnu.kmp.domain.posts.usecase.GetPostsUseCase
import ua.chnu.kmp.domain.posts.usecase.UpdatePostUseCase
import ua.chnu.kmp.presentation.AppViewModel

val networkModule = module {
    single {
        HttpClient {
            install(Logging) {
                level = LogLevel.ALL
                logger = object : Logger {
                    override fun log(message: String) {
                        println("KtorLogger: $message")
                    }
                }
            }
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
        }
    }

    singleOf(::AppPostApiService) { bind<PostApiService>() }
}
val domainModule = module {
    factoryOf(::GetPostsUseCase)
    factoryOf(::CreatePostUseCase)
    factoryOf(::UpdatePostUseCase)
    factoryOf(::DeletePostUseCase)
}

val appModule = module {
    includes(networkModule, domainModule)
    singleOf(::AppPostRepository) { bind<PostRepository>() }
    viewModelOf(::AppViewModel)
}
