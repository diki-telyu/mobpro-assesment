package org.d3if0166.dailytask.network

import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.d3if0166.dailytask.model.OpStatus
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
//import okhttp3.OkHttpClient
//import okhttp3.RequestBody

private const val BASE_URL = "https://kascbelrfcchntazlrty.supabase.co/rest/v1/"

private val moshi = Moshi.Builder()
    .add(com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory())
    .build()

//val logging = HttpLoggingInterceptor().apply {
//    setLevel(HttpLoggingInterceptor.Level.BODY)
//}

//val client = OkHttpClient.Builder()
//    .addInterceptor(logging)
//    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface UserApiService {
    @Headers(
        "apiKey: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imthc2NiZWxyZmNjaG50YXpscnR5Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTgwMjY4NjMsImV4cCI6MjAzMzYwMjg2M30.eJvywCHCZTOkYXmjASxORAozv2G6sVpB1KsvhlHEuKA",
        "Content-Type: application/json"
    )
    @Multipart
    @POST("users")
    suspend fun addUser(
//        @Part("user_id") userId: RequestBody,
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("photo_url") photoUrl: RequestBody,
    ): OpStatus
}

object UserApi {
    val service: UserApiService by lazy {
        retrofit.create(UserApiService::class.java)
    }
}