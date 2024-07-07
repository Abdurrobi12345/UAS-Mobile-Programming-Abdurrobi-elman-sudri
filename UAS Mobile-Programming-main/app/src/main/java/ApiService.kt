package com.abdurrobi.loginactivity

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("your/endpoint")
    fun fetchData(): Call<UnPam>
}
