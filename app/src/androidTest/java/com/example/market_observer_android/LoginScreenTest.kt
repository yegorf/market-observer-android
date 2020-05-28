package com.example.market_observer_android

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.market_observer_android.presentation.activity.GlobalActivity
import com.example.market_observer_android.presentation.activity.LoginActivity
import org.hamcrest.Matchers.allOf
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class LoginScreenTest {

    private val rightEmail = "user1@gmail.com"
    private val rightPassword = "password1"
    private val wrongEmail = "wrong_user@gmail.com"
    private val wrongPassword = "wrong_password"

    var activityRule = ActivityTestRule(LoginActivity::class.java)

    @Test
    fun loginWithRightCredentials() {
        activityRule.launchActivity(Intent())

        onView(
            allOf(
                withId(R.id.styled_et_input),
                ViewMatchers.isDescendantOfA(withId(R.id.login_email_input))
            )
        ).perform(ViewActions.typeText(rightEmail))

        onView(
            allOf(
                withId(R.id.styled_et_input),
                ViewMatchers.isDescendantOfA(withId(R.id.login_password_input))
            )
        ).perform(ViewActions.typeText(rightPassword))

        onView(withId(R.id.login_button)).perform(click())

        Intents.init()
        waitSeconds(5)
        intended(hasComponent(GlobalActivity::class.java.name))
    }

    @Test
    fun loginWithWrongEmail() {
        activityRule.launchActivity(Intent())

        onView(
            allOf(
                withId(R.id.styled_et_input),
                ViewMatchers.isDescendantOfA(withId(R.id.login_email_input))
            )
        ).perform(ViewActions.typeText(wrongEmail))

        onView(
            allOf(
                withId(R.id.styled_et_input),
                ViewMatchers.isDescendantOfA(withId(R.id.login_password_input))
            )
        ).perform(ViewActions.typeText(rightPassword))

        onView(withId(R.id.login_button)).perform(click())
    }

    @Test
    fun loginWithWrongPassword() {
        activityRule.launchActivity(Intent())

        onView(
            allOf(
                withId(R.id.styled_et_input),
                ViewMatchers.isDescendantOfA(withId(R.id.login_email_input))
            )
        ).perform(ViewActions.typeText(rightEmail))

        onView(
            allOf(
                withId(R.id.styled_et_input),
                ViewMatchers.isDescendantOfA(withId(R.id.login_password_input))
            )
        ).perform(ViewActions.typeText(wrongPassword))

        onView(withId(R.id.login_button)).perform(click())
    }

    private fun waitSeconds(seconds: Long) {
        Thread.sleep(seconds * 1000)
    }
}
