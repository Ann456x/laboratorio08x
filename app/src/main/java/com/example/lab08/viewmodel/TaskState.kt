package com.example.lab08.viewmodel

import com.example.lab08.data.Task

data class TaskState(
    val tasks: List<Task> = emptyList(),
    val newTaskDescription: String = ""
)