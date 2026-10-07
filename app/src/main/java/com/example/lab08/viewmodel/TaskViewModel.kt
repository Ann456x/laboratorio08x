package com.example.lab08

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TaskViewModel(private val dao: TaskDao) : ViewModel() {

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks

    private val _filter = MutableStateFlow("Todas")
    val filter: StateFlow<String> = _filter

    init {
        loadTasks()
    }

    fun loadTasks() {
        viewModelScope.launch {
            _tasks.value = dao.getAllTasks()
        }
    }

    fun setFilter(newFilter: String) {
        _filter.value = newFilter
    }

    fun addTask(description: String) {
        viewModelScope.launch {
            dao.insertTask(Task(description = description))
            loadTasks()
        }
    }

    fun toggleTaskCompletion(task: Task) {
        viewModelScope.launch {
            val updated = task.copy(isCompleted = !task.isCompleted)
            dao.updateTask(updated)
            loadTasks()
        }
    }

    fun updateTaskDescription(task: Task, newDescription: String) {
        viewModelScope.launch {
            val updated = task.copy(description = newDescription)
            dao.updateTask(updated)
            loadTasks()
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            dao.deleteTask(task)
            loadTasks()
        }
    }

    fun deleteAllTasks() {
        viewModelScope.launch {
            dao.deleteAllTasks()
            loadTasks()
        }
    }
}