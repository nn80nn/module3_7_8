package com.example.module3_7_8.data.repository

import com.example.module3_7_8.data.local.TodoJsonDataSource
import com.example.module3_7_8.domain.model.TodoItem
import com.example.module3_7_8.domain.repository.TodoRepository

class TodoRepositoryImpl(
    private val dataSource: TodoJsonDataSource
) : TodoRepository {
    private var cachedTodos: MutableList<TodoItem>? = null

    override suspend fun getTodos(): List<TodoItem> {
        if (cachedTodos == null) {
            cachedTodos = dataSource.getTodos().map { dto ->
                TodoItem(
                    id = dto.id,
                    title = dto.title,
                    description = dto.description,
                    isCompleted = dto.isCompleted
                )
            }.toMutableList()
        }
        return cachedTodos ?: emptyList()
    }

    override suspend fun toggleTodo(id: Int) {
        cachedTodos?.let { todos ->
            val index = todos.indexOfFirst { it.id == id }
            if (index != -1) {
                val todo = todos[index]
                todos[index] = todo.copy(isCompleted = !todo.isCompleted)
            }
        }
    }
}
