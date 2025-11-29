package com.example.module3_7_8.domain.usecase

import com.example.module3_7_8.domain.repository.TodoRepository

class AddTodoUseCase(private val repository: TodoRepository) {
    suspend operator fun invoke(title: String, description: String) {
        repository.addTodo(title, description)
    }
}
