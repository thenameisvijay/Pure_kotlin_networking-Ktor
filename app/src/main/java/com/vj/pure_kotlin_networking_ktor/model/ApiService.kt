package com.vj.pure_kotlin_networking_ktor.model

import com.vj.pure_kotlin_networking_ktor.BuildConfig
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import javax.inject.Inject

class ApiService @Inject constructor() {

    private val client = HttpClient(Android){
        install(DefaultRequest){
            headers.append("Content-Type", "application/json")
        }
        install(JsonFeature){
            serializer = GsonSerializer()
        }
        engine {
            connectTimeout = 100_000
            socketTimeout = 100_000
        }
    }

    suspend fun requestUserData() : ArrayList<RepoResponse> {
        return client.get {
          url(BuildConfig.BASE_URL+"users")
//          url("https://api.github.com/users")
        }
    }
}