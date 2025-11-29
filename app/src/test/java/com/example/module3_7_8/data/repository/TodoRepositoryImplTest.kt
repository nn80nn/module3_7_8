package com.example.module3_7_8.data.repository

import com.example.module3_7_8.data.local.TodoJsonDataSource
import com.example.module3_7_8.data.model.TodoItemDto
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class TodoRepositoryImplTest {

    private lateinit var dataSource: TodoJsonDataSource
    private lateinit var repository: TodoRepositoryImpl

    @Before
    fun setup() {
        dataSource = mockk()
        // Подготовка тестовых данных
        every { dataSource.getTodos() } returns listOf(
            TodoItemDto(1, "Купить молоко", "2 литра, обезжиренное", false),
            TodoItemDto(2, "Позвонить маме", "Спросить про выходные", true),
            TodoItemDto(3, "Сделать ДЗ по Android", "Clean Architecture + Compose", false)
        )
        repository = TodoRepositoryImpl(dataSource)
    }

    @Test
    fun `toggleTodo changes isCompleted from false to true`() = runTest {
        // Given - задача с isCompleted = false
        val todos = repository.getTodos()
        val todoId = 1
        val todoBefore = todos.find { it.id == todoId }
        assertFalse(todoBefore!!.isCompleted)

        // When - переключаем статус
        repository.toggleTodo(todoId)

        // Then - isCompleted должен стать true
        val todosAfter = repository.getTodos()
        val todoAfter = todosAfter.find { it.id == todoId }
        assertTrue(todoAfter!!.isCompleted)
    }

    @Test
    fun `toggleTodo changes isCompleted from true to false`() = runTest {
        // Given - задача с isCompleted = true
        val todos = repository.getTodos()
        val todoId = 2
        val todoBefore = todos.find { it.id == todoId }
        assertTrue(todoBefore!!.isCompleted)

        // When - переключаем статус
        repository.toggleTodo(todoId)

        // Then - isCompleted должен стать false
        val todosAfter = repository.getTodos()
        val todoAfter = todosAfter.find { it.id == todoId }
        assertFalse(todoAfter!!.isCompleted)
    }

    @Test
    fun `toggleTodo twice returns to original state`() = runTest {
        // Given
        val todoId = 1
        val todosBefore = repository.getTodos()
        val originalState = todosBefore.find { it.id == todoId }!!.isCompleted

        // When - переключаем дважды
        repository.toggleTodo(todoId)
        repository.toggleTodo(todoId)

        // Then - состояние должно вернуться к исходному
        val todosAfter = repository.getTodos()
        val finalState = todosAfter.find { it.id == todoId }!!.isCompleted
        assertEquals(originalState, finalState)
    }
}
