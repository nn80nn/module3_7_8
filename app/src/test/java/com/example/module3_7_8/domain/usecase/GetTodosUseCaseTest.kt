package com.example.module3_7_8.domain.usecase

import com.example.module3_7_8.domain.model.TodoItem
import com.example.module3_7_8.domain.repository.TodoRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetTodosUseCaseTest {

    private lateinit var repository: TodoRepository
    private lateinit var getTodosUseCase: GetTodosUseCase

    @Before
    fun setup() {
        repository = mockk()
        getTodosUseCase = GetTodosUseCase(repository)
    }

    @Test
    fun `GetTodosUseCase returns 3 tasks`() = runTest {
        // Given - подготовка тестовых данных
        val expectedTodos = listOf(
            TodoItem(1, "Купить молоко", "2 литра, обезжиренное", false),
            TodoItem(2, "Позвонить маме", "Спросить про выходные", true),
            TodoItem(3, "Сделать ДЗ по Android", "Clean Architecture + Compose", false)
        )
        coEvery { repository.getTodos() } returns expectedTodos

        // When - выполнение тестируемого действия
        val result = getTodosUseCase()

        // Then - проверка результата
        assertEquals(3, result.size)
        assertEquals(expectedTodos, result)
    }

    @Test
    fun `GetTodosUseCase returns empty list when repository is empty`() = runTest {
        // Given
        coEvery { repository.getTodos() } returns emptyList()

        // When
        val result = getTodosUseCase()

        // Then
        assertEquals(0, result.size)
        assertEquals(emptyList<TodoItem>(), result)
    }
}
