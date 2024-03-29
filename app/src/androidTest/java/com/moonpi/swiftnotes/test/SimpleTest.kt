package com.moonpi.swiftnotes.test

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.runner.AndroidJUnit4
import android.widget.ImageButton
import com.moonpi.swiftnotes.MainActivity
import com.moonpi.swiftnotes.R
import com.moonpi.swiftnotes.rule.SwiftnotesRule
import org.hamcrest.CoreMatchers.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.tinkoff.allure.android.deviceScreenshot
import ru.tinkoff.allure.annotations.DisplayName
import ru.tinkoff.allure.step

@RunWith(AndroidJUnit4::class)
@DisplayName("Создание заметки")
class SimpleTest : AbstractSwiftnotesTest() {

    @get:Rule
    val rule = SwiftnotesRule(MainActivity::class.java, false)

    @Test
    @DisplayName("Проверка открытия страницы создания")
    fun newNoteHints() {
        rule.launchActivity()
        step("Проверяем отображение страницы") {
            onView(withId(R.id.newNote)).perform(click())
            onView(allOf(withId(R.id.titleEdit), isDisplayed())).check(matches(withHint("Title")))
            onView(allOf(withId(R.id.bodyEdit), isDisplayed())).check(matches(withHint("Note")))
            deviceScreenshot("page_display")
        }
    }

    @Test
    @DisplayName("Проверка экрана создания заметки")
    fun noteCreationScreen() {
        rule.launchActivity()
        step("Зайти на форму создания заметки") {
            onView(withId(R.id.newNote)).perform(click())
            onView(allOf(withId(R.id.titleEdit), isDisplayed())).check(matches(withHint("Title")))
            onView(allOf(withId(R.id.bodyEdit), isDisplayed())).check(matches(withHint("Note")))
            onView(allOf(withId(R.id.toolbar), isDisplayed()))
        }
        step("Выйти с формы создания заметки") {
            onView(withClassName(containsString("ImageButton"))).perform(click())
            onView(allOf(withClassName(containsString("FrameLayout")), isDisplayed()))
            onView(allOf(withClassName(containsString("TextView")), isDisplayed())).check(matches(withText("Save changes?")))
            onView(allOf(withId(android.R.id.button1), isDisplayed())).check(matches(withText("YES")))
            onView(allOf(withId(android.R.id.button2), isDisplayed())).check(matches(withText("NO")))
            onView(withId(android.R.id.button2)).perform(click())
            onView(allOf(withId(R.id.toolbar), isDisplayed()))
            onView(allOf(withId(R.id.toolbar), withChild(withText("Swiftnotes"))))
        }
    }
}
