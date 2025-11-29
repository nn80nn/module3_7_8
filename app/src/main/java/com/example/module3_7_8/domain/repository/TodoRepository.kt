package com.example.module3_7_8.domain.repository

import com.example.module3_7_8.domain.model.TodoItem

interface TodoRepository {
    suspend fun getTodos(): List<TodoItem>
    suspend fun toggleTodo(id: Int)
    suspend fun addTodo(title: String, description: String)
    suspend fun deleteTodo(id: Int)
}
