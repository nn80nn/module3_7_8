package com.example.module3_7_8

import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TodoListScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testDisplaysAllThreeTodosFromJson() {
        // Проверяем, что отображаются все 3 задачи из JSON файла
        composeTestRule.onNodeWithText("Купить молоко").assertIsDisplayed()
        composeTestRule.onNodeWithText("Позвонить маме").assertIsDisplayed()
        composeTestRule.onNodeWithText("Сделать ДЗ по Android").assertIsDisplayed()

        // Проверяем описания
        composeTestRule.onNodeWithText("2 литра, обезжиренное").assertIsDisplayed()
        composeTestRule.onNodeWithText("Спросить про выходные").assertIsDisplayed()
        composeTestRule.onNodeWithText("Clean Architecture + Compose").assertIsDisplayed()
    }

    @Test
    fun testCheckboxTogglesStatus() {
        // Given - находим все чекбоксы
        val checkboxes = composeTestRule.onAllNodes(isToggleable())

        // Получаем первый чекбокс (для задачи "Купить молоко", которая не выполнена)
        val firstCheckbox = checkboxes[0]

        // Проверяем начальное состояние (не отмечен)
        firstCheckbox.assertIsOff()

        // When - кликаем на чекбокс
        firstCheckbox.performClick()

        // Then - чекбокс должен быть отмечен
        firstCheckbox.assertIsOn()

        // When - кликаем снова
        firstCheckbox.performClick()

        // Then - чекбокс снова не отмечен
        firstCheckbox.assertIsOff()
    }

    @Test
    fun testCheckboxTogglesStatusForCompletedTask() {
        // Given - находим все чекбоксы
        val checkboxes = composeTestRule.onAllNodes(isToggleable())

        // Получаем второй чекбокс (для задачи "Позвонить маме", которая выполнена)
        val secondCheckbox = checkboxes[1]

        // Проверяем начальное состояние (отмечен)
        secondCheckbox.assertIsOn()

        // When - кликаем на чекбокс
        secondCheckbox.performClick()

        // Then - чекбокс должен быть не отмечен
        secondCheckbox.assertIsOff()
    }

    @Test
    fun testNavigationFromListToDetailAndBack() {
        // Given - главный экран со списком задач
        composeTestRule.onNodeWithText("Купить молоко").assertIsDisplayed()

        // When - кликаем на задачу (по тексту)
        composeTestRule.onNodeWithText("Купить молоко").performClick()

        // Then - переходим на экран деталей
        composeTestRule.waitUntil(5000) {
            composeTestRule.onAllNodesWithText("Todo Details").fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithText("Todo Details").assertIsDisplayed()
        composeTestRule.onNodeWithText("Title").assertIsDisplayed()
        composeTestRule.onNodeWithText("Description").assertIsDisplayed()
        composeTestRule.onNodeWithText("Status").assertIsDisplayed()

        // When - нажимаем кнопку "Назад"
        composeTestRule.onNodeWithContentDescription("Back").performClick()

        // Then - возвращаемся на главный экран
        composeTestRule.waitUntil(5000) {
            composeTestRule.onAllNodesWithText("Todo List").fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithText("Todo List").assertIsDisplayed()
        composeTestRule.onNodeWithText("Купить молоко").assertIsDisplayed()
    }

    @Test
    fun testAddNewTodoButton() {
        // Given - кнопка добавления задачи
        val addButton = composeTestRule.onNodeWithContentDescription("Add Todo")

        // Then - кнопка отображается
        addButton.assertIsDisplayed()

        // When - нажимаем на кнопку
        addButton.performClick()

        // Then - открывается диалог добавления
        composeTestRule.onNodeWithText("Add New Todo").assertIsDisplayed()
        composeTestRule.onNodeWithText("Title").assertIsDisplayed()
        composeTestRule.onNodeWithText("Description").assertIsDisplayed()
    }

    @Test
    fun testDeleteTodoFromList() {
        // Given - находим первую иконку удаления
        val deleteButtons = composeTestRule.onAllNodesWithContentDescription("Delete")

        // Проверяем, что задача существует
        composeTestRule.onNodeWithText("Купить молоко").assertIsDisplayed()

        // When - нажимаем на кнопку удаления для первой задачи
        deleteButtons[0].performClick()

        // Then - задача удалена и не отображается
        composeTestRule.waitUntil(5000) {
            composeTestRule.onAllNodesWithText("Купить молоко").fetchSemanticsNodes().isEmpty()
        }
        composeTestRule.onNodeWithText("Купить молоко").assertDoesNotExist()
    }
}
