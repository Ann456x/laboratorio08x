package com.example.lab08.viewmodel

import com.example.lab08.Task

data class TaskState(
    val tasks: List<Task> = emptyList(),
    val filter: String = "Todas"
)