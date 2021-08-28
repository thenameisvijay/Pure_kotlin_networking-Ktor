package com.vj.pure_kotlin_networking_ktor.di

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import okhttp3.Interceptor
import okhttp3.Protocol.Companion.get
import okhttp3.Response
import org.slf4j.event.Level
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
//                addInterceptor(CurlInterceptor(Loggable { Log.v("Curl", it) }))
//                val loggingInterceptor = HttpLoggingInterceptor()
//                loggingInterceptor.level = LogLevel.BODY
//                addInterceptor(loggingInterceptor)
                
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

        }
    }

    private val json = kotlinx.serialization.json.Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = false
    }

}