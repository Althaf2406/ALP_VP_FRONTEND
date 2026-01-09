package com.example.alp_vp_frontend.data.service

import com.example.alp_vp_frontend.data.model.*
import retrofit2.http.*

interface ItemApiService {

    // --- GENERAL SUMMARY/ALL ITEMS ---
    @GET("summary")
    suspend fun getSummary(): Summary

    @GET("all")
    suspend fun getAllItems(): List<Item>

    // --- TASK CRUD ---
    @GET("tasks/{id}")
    suspend fun getTaskById(@Path("id") id: String): Task
    @GET("tasks")
    suspend fun getAllTasks(): List<Task>
    @POST("tasks")
    suspend fun createTask(@Body task: Task): Task // Menggunakan Model Task
    @PUT("tasks/{id}")
    suspend fun updateTask(@Path("id") id: String, @Body task: Task): Task // Menggunakan Model Task
    @DELETE("tasks/{id}")
    suspend fun deleteTask(@Path("id") id: String): Unit?

    // --- ACTIVITY CRUD ---
    @GET("activities/{id}")
    suspend fun getActivityById(@Path("id") id: String): Activity
    @GET("activities")
    suspend fun getAllActivities(): List<Activity>
    @POST("activities")
    suspend fun createActivity(@Body activity: Activity): Activity // Menggunakan Model Activity
    @PUT("activities/{id}")
    suspend fun updateActivity(@Path("id") id: String, @Body activity: Activity): Activity // Menggunakan Model Activity
    @DELETE("activities/{id}")
    suspend fun deleteActivity(@Path("id") id: String): Unit?
}