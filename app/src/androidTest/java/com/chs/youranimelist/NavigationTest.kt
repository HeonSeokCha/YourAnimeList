package com.chs.youranimelist

import android.app.Application
import android.content.Context
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
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
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CustomTestRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}


@HiltAndroidTest
class NavigationTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    lateinit var navController: TestNavHostController

//    @Before
//    fun mainNavHost() {
//        composeTestRule.activity.setContent {
//            navController = TestNavHostController(LocalContext.current)
//            navController.navigatorProvider.addNavigator(ComposeNavigator())
//        }
//    }

    @Test
    fun mainNavHost_verifyHomeStartDestination() {
        composeTestRule
            .onNodeWithText("Home")
            .assertIsSelected()

//        val route = navController.currentBackStackEntry?.destination?.route
//        assertEquals(route, "home_screen")
    }

    @Test
    fun mainNavHost_clickAnimeTab_navigateAnimeTab() {
        composeTestRule.onNodeWithText("Anime")
            .performClick()

        composeTestRule
            .onNodeWithText("Anime")
            .assertIsSelected()

//        val route = navController.currentBackStackEntry?.destination?.route
//        assertEquals(route, "animeList_screen")
    }

    @Test
    fun mainNavHost_clickCharaTab_navigateCharaTab() {
        composeTestRule.onNodeWithText("Character")
            .performClick()

        composeTestRule
            .onNodeWithText("Character")
            .assertIsSelected()

//        val route = navController.currentBackStackEntry?.destination?.route
//        assertEquals(route, "charaList_screen")
    }


    @Test
    fun mainNavHost_clickSearch_animeTab() {
        composeTestRule
            .onNodeWithContentDescription("home_screen_search")
            .performClick()

        composeTestRule.onNodeWithText("ANIME")
            .performClick()

        composeTestRule
            .onNodeWithText("ANIME")
            .assertIsSelected()
    }

    @Test
    fun mainNavHost_clickSearch_mangaTab() {
        composeTestRule
            .onNodeWithContentDescription("home_screen_search")
            .performClick()

        composeTestRule.onNodeWithText("MANGA")
            .performClick()

        composeTestRule
            .onNodeWithText("MANGA")
            .assertIsSelected()
    }

    @Test
    fun mainNavHost_clickSearch_charaTab() {
        composeTestRule
            .onNodeWithContentDescription("home_screen_search")
            .performClick()

        composeTestRule.onNodeWithText("CHARACTER")
            .performClick()

        composeTestRule
            .onNodeWithText("CHARACTER")
            .assertIsSelected()

    }
}