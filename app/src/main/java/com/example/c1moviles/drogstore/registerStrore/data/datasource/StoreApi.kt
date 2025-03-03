package com.example.c1moviles.drogstore.registerStrore.data.datasource

import com.example.c1moviles.drogstore.registerStrore.data.model.Farmacia
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.gson.gson

suspend fun postStore(store: Farmacia): Boolean {
    val client = HttpClient(Android) {
        install(ContentNegotiation) {
            gson() // Usa Gson para la serialización
        }
    }
    return try {
        val response: HttpResponse = client.post("http://10.0.2.2:8080/api/app/ssv/farmacia") {
            contentType(ContentType.Application.Json)
            setBody(store)
        }

        response.status == HttpStatusCode.OK
    } catch (e: Exception) {
        e.printStackTrace()
        false
    } finally {
        client.close()
    }
}