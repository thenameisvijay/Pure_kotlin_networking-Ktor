package com.vj.pure_kotlin_networking_ktor.di

import android.util.Log
import com.vj.pure_kotlin_networking_ktor.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.client.response.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideHeadersInterceptor() = Interceptor { chain ->
        val requestBuilder = chain.request().newBuilder()
            .addHeader("Content-Type", "application/vnd.api+json")
        chain.proceed(requestBuilder.build())
    }

    @Provides
    @Singleton
    fun providesKtorClientInstance(): HttpClient {
        return HttpClient(OkHttp) {
            engine {
                config {
                    followRedirects(true)
                }
                addNetworkInterceptor(provideHeadersInterceptor())
            }

            //Serialize
            install(JsonFeature) {
                serializer = KotlinxSerializer(json)//serializer = KotlinxSerializer(Json.nonstrict)
            }

            // Logging
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.v("Ktor", message)
                    }
                }
                level = LogLevel.ALL
            }

            // Timeout
            install(HttpTimeout) {
                requestTimeoutMillis = 15000L
                connectTimeoutMillis = 15000L
                socketTimeoutMillis = 15000L
            }

            defaultRequest { // this: HttpRequestBuilder ->
                URLBuilder(BuildConfig.BASE_URL)
            }


        }
    }

    private val json = kotlinx.serialization.json.Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = false
    }

    @Provides
    @Singleton
    fun provideKtorInstance(): HttpClient {
        return HttpClient(Android) {
            install(DefaultRequest) {
                headers.append("Content-Type", "application/json")
            }
            install(JsonFeature) {
                serializer = KotlinxSerializer(json)
            }
            engine {
                connectTimeout = 100_000
                socketTimeout = 100_000
            }
        }
    }


}