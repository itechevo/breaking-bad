package com.itechevo.breakingbad.ui.characters

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.itechevo.breakingbad.R
import com.itechevo.breakingbad.ui.model.Loaded
import com.itechevo.breakingbad.ui.model.Loading
import com.itechevo.domain.model.Character
import org.hamcrest.Matchers
import org.junit.Test

class CharactersFragmentTest {

    @Test
    fun onLaunchThenProgressBarDisplayed() {
        val scenario = launchFragmentInContainer<CharactersFragment>()

        scenario.onFragment {
            it.handleResult(Loading)
        }

        onView(withId(R.id.progress))
            .check(matches(isDisplayed()))
    }

    @Test
    fun onCharacterListLoadedThenCharactersDisplayed() {
        val scenario = launchFragmentInContainer<CharactersFragment>()

        scenario.onFragment {
            it.handleResult(Loaded<List<Character>>(emptyList()))
        }

        onView(withId(R.id.characterList))
            .check(matches(isDisplayed()))

        onView(withId(R.id.progress))
            .check(matches(Matchers.not(isDisplayed())))
    }
}