package com.example.module3_7_8.domain.usecase

import com.example.module3_7_8.domain.repository.TodoRepository

class DeleteTodoUseCase(private val repository: TodoRepository) {
    suspend operator fun invoke(id: Int) {
        repository.deleteTodo(id)
    }
}
