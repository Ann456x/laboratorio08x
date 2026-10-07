package com.example.lab08.repository

import com.example.lab08.Task
import com.example.lab08.TaskDao

class TaskRepository(private val taskDao: TaskDao) {
    suspend fun getAllTasks(): List<Task> = taskDao.getAllTasks()
    suspend fun insertTask(task: Task) = taskDao.insertTask(task)
    suspend fun updateTask(task: Task) = taskDao.updateTask(task)
    suspend fun deleteTask(task: Task) = taskDao.deleteTask(task)
}