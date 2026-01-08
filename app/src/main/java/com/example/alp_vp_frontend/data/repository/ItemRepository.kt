package com.example.alp_vp_frontend.data.repository

import com.example.alp_vp_frontend.data.model.*
import com.example.alp_vp_frontend.data.service.ItemApiService

interface ItemRepository {
    suspend fun getSummary(): Summary
    suspend fun getAllItems(): List<Item>

    // Task CRUD
    suspend fun getAllTasks(): List<Task>
    suspend fun createTask(task: Task): Task
    suspend fun updateTask(task: Task): Task
    suspend fun deleteTask(taskId: String)

    // Activity CRUD
    suspend fun getAllActivities(): List<Activity>
    suspend fun createActivity(activity: Activity): Activity
    suspend fun updateActivity(activity: Activity): Activity
    suspend fun deleteActivity(activityId: String)
}


class ItemRepositoryImpl constructor(
    private val apiService: ItemApiService
) : ItemRepository {

    override suspend fun getSummary(): Summary {
        return apiService.getSummary()
    }

    override suspend fun getAllItems(): List<Item> {
        return apiService.getAllItems()
    }

    // Task CRUD Implementation
    override suspend fun getAllTasks(): List<Task> {
        return apiService.getAllTasks()
    }

    override suspend fun createTask(task: Task): Task {
        return apiService.createTask(task)
    }

    override suspend fun updateTask(task: Task): Task {
        // Mengirim seluruh model untuk update, pastikan ID ada
        return apiService.updateTask(task.id!!, task)
    }

    override suspend fun deleteTask(taskId: String) {
        apiService.deleteTask(taskId)
    }

    // Activity CRUD Implementation
    override suspend fun getAllActivities(): List<Activity> {
        return apiService.getAllActivities()
    }

    override suspend fun createActivity(activity: Activity): Activity {
        return apiService.createActivity(activity)
    }

    override suspend fun updateActivity(activity: Activity): Activity {
        // Mengirim seluruh model untuk update, pastikan ID ada
        return apiService.updateActivity(activity.id!!, activity)
    }

    override suspend fun deleteActivity(activityId: String) {
        apiService.deleteActivity(activityId)
    }
}