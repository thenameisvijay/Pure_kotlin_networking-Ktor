package com.vj.pure_kotlin_networking_ktor.model

import android.util.Log
import com.vj.pure_kotlin_networking_ktor.BuildConfig
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import javax.inject.Inject

class ApiService @Inject constructor() {

    private val client = HttpClient(Android) {
        install(DefaultRequest) {
            header("Content-Type", "application/json")
        }
        install(JsonFeature) {
            serializer = GsonSerializer()
        }
        engine {
            connectTimeout = 100_000
            socketTimeout = 100_000
        }

        // Logging
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.i("Network Log ", message)
                }
            }
            level = LogLevel.ALL
        }
    }

    suspend fun requestUserData(path:String): ArrayList<RepoResponse> {
        return client.get {
            url(BuildConfig.BASE_URL + path)
        }
    }
}