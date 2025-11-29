package com.example.module3_7_8.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.module3_7_8.data.local.TodoJsonDataSource
import com.example.module3_7_8.data.repository.TodoRepositoryImpl
import com.example.module3_7_8.domain.model.TodoItem
import com.example.module3_7_8.domain.usecase.GetTodosUseCase
import com.example.module3_7_8.domain.usecase.ToggleTodoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TodoViewModel(context: Context) : ViewModel() {
    private val dataSource = TodoJsonDataSource(context)
    private val repository = TodoRepositoryImpl(dataSource)
    private val getTodosUseCase = GetTodosUseCase(repository)
    private val toggleTodoUseCase = ToggleTodoUseCase(repository)

    private val _todos = MutableStateFlow<List<TodoItem>>(emptyList())
    val todos: StateFlow<List<TodoItem>> = _todos.asStateFlow()

    init {
        loadTodos()
    }

    private fun loadTodos() {
        viewModelScope.launch {
            _todos.value = getTodosUseCase()
        }
    }

    fun toggleTodo(id: Int) {
        viewModelScope.launch {
            toggleTodoUseCase(id)
            _todos.value = getTodosUseCase()
        }
    }
}
