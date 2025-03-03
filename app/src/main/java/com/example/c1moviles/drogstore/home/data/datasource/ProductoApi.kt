package com.example.c1moviles.drogstore.home.data.datasource

import com.example.c1moviles.drogstore.home.data.model.Producto
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.gson.*

suspend fun postProducto(producto: Producto): Boolean {
    val client = HttpClient(Android) {
        install(ContentNegotiation) {
            gson()
        }
    }
    return try {
        val response: HttpResponse = client.post("http://10.0.2.2:8080/api/app/ssv/mds") {
            contentType(ContentType.Application.Json)
            setBody(producto)
        }

        response.status == HttpStatusCode.OK
    } catch (e: Exception) {
        e.printStackTrace()
        false
    } finally {
        client.close()
    }
}

suspend fun getProductos(): List<Producto>? {
    val client = HttpClient(Android) {
        install(ContentNegotiation) {
            gson()
        }
    }

    return try {
        val response: HttpResponse = client.get("http://10.0.2.2:8080/api/app/ssv/getMds")
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