package com.example.lab08.repository

import com.example.lab08.data.TaskDao
import com.example.lab08.data.Task
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {
    val tasks: Flow<List<Task>> = taskDao.getAllTasks()

    suspend fun addTask(description: String) {
        if (description.isNotBlank()) {
            taskDao.insertTask(Task(description = description))
        }
    }

    suspend fun toggleTaskStatus(task: Task) {
        taskDao.updateTask(task.copy(isCompleted = !task.isCompleted))
    }

    suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }
}