package com.chs.youranimelist

import android.app.Application
import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.runner.AndroidJUnitRunner
import com.chs.youranimelist.presentation.main.MainActivity
import com.chs.youranimelist.presentation.main.MainNavHost
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.testing.CustomTestApplication
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CustomTestRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}

@AndroidEntryPoint
class HiltTestActivity : ComponentActivity()

@HiltAndroidTest
class NavigationTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    lateinit var navController: TestNavHostController

    @Before
    fun mainNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(
                ComposeNavigator()
            )
            MainNavHost(navController = navController, searchListQuery = "", searchQuery = "")
        }
    }

    @Test
    fun mainNavHost_verifyHomeStartDestination() {
        composeTestRule
            .onNodeWithContentDescription("YourAnimeList")
            .assertIsDisplayed()
    }
}