package com.example.c1moviles.drogstore.home.data.datasource

import com.example.c1moviles.drogstore.home.data.model.Producto
import com.example.c1moviles.drogstore.registerStrore.data.model.Farmacia
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.gson.gson

suspend fun getFarmacias(): List<Farmacia>? {
    val client = HttpClient(Android) {
        install(ContentNegotiation) {
            gson()
        }
    }

    return try {
        val response: HttpResponse = client.get("http://10.0.2.2:8080/api/app/ssv/getFarmacias")
        if (response.status == HttpStatusCode.OK) {
            response.body()
        } else {
            null
        }
    } catch (e: Exception) {
        null
    } finally {
        client.close()
    }

}