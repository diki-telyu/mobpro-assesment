package org.d3if0166.dailytask.network

import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import okhttp3.RequestBody
import org.d3if0166.dailytask.model.OpStatus
import org.d3if0166.dailytask.model.Task
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

private const val BASE_URL = "https://kascbelrfcchntazlrty.supabase.co/rest/v1/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface TaskApiService {
    @Headers(
        "apiKey: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imthc2NiZWxyZmNjaG50YXpscnR5Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTgwMjY4NjMsImV4cCI6MjAzMzYwMjg2M30.eJvywCHCZTOkYXmjASxORAozv2G6sVpB1KsvhlHEuKA"
    )
    @GET("tasks")
    suspend fun getTasks(
        @Query("user_id") email: String,
        @Query("is_completed") isCompleted: String = "eq.${false}"
    ): List<Task>

    @Headers(
        "apiKey: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imthc2NiZWxyZmNjaG50YXpscnR5Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTgwMjY4NjMsImV4cCI6MjAzMzYwMjg2M30.eJvywCHCZTOkYXmjASxORAozv2G6sVpB1KsvhlHEuKA"
    )
    @GET("tasks")
    suspend fun getTaskById(
        @Query("task_id") taskId: Long,
    ): List<Task>

    @Multipart
    @POST("tasks")
    suspend fun addTaskByUserId(
        @Header("apiKey") apiKey: String,
        @Part("name") name: RequestBody,
        @Part("detail") detail: RequestBody,
        @Part("due_date") due_date: RequestBody,
        @Part("user_id") user_id: RequestBody
    ): OpStatus
}

object TaskApi {
    val service: TaskApiService by lazy {
        retrofit.create(TaskApiService::class.java)
    }
}