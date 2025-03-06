package com.example.c1moviles.drogstore.login.data.datasource

import com.example.c1moviles.drogstore.login.data.model.User
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.gson.gson

suspend fun postUser(user: User): Result<Map<String, Any>> {
    val client = HttpClient(Android) {
        install(ContentNegotiation) {
            gson()
        }
    }
    return try {
        val response: HttpResponse = client.post("http://10.0.2.2:8080/user/signin") {
            contentType(ContentType.Application.Json)
            setBody(user)
        }

        if (response.status == HttpStatusCode.OK) {
            val responseBody: Map<String, Any> = response.body()
            Result.success(responseBody)
        } else {
            Result.failure(Exception("Error en el login: ${response.status}"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    } finally {
        client.close()
    }
}
