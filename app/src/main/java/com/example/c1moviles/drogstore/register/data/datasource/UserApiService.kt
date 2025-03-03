package com.example.c1moviles.drogstore.register.data.datasource


import com.example.c1moviles.drogstore.register.data.model.UserRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApiService {
    @POST("api/solver/signup")
    suspend fun registerUser(@Body user: UserRequest): Response<Void>
}