package com.itechevo.breakingbad.ui.characters

import androidx.lifecycle.Observer
import com.itechevo.breakingbad.ui.model.Loaded
import com.itechevo.breakingbad.ui.model.Loading
import com.itechevo.breakingbad.ui.model.LoadingError
import com.itechevo.breakingbad.ui.model.ViewState
import com.itechevo.domain.model.Character
import com.itechevo.domain.usecase.CharacterUseCase
import io.mockk.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class CharactersViewModelTest : BaseViewModelTest() {

    private val characterUseCase = mockk<CharacterUseCase>()

    private val observer = mockk<Observer<ViewState>>(relaxUnitFun = true)

    private val charactersViewModel = CharactersViewModel(characterUseCase)

    private val characters = listOf(
        Character(
            1, "Will", emptyList(), "", "", "", listOf(1, 2, 3, 4)
        ), Character(
            2, "Sam", emptyList(), "", "", "", listOf(1, 2, 3)
        ), Character(
            3, "Alex", emptyList(), "", "", "", listOf(1, 2)
        )
    )

    @Before
    override fun setUp() {
        super.setUp()
        charactersViewModel.viewState.observeForever(observer)
    }

    @Test
    fun `given characters when getCharacters then return characters`() {
        runBlocking {
            coEvery { characterUseCase.getCharacters() } returns flowOf(characters)

            charactersViewModel.getCharacters()

            val slot = slot<ViewState>()

            verifyOrder {
                observer.onChanged(Loading)
                observer.onChanged(capture(slot))
            }

            val loaded = slot.captured as Loaded<List<Character>>

            assertNotNull(loaded)
            assertEquals(3, loaded.data.size)

            coVerify { characterUseCase.getCharacters() }
        }
    }

    @Test
    fun `given error when getCharacters then return error`() {
        runBlocking {
            val error = Exception("Something went wrong!")

            coEvery { characterUseCase.getCharacters() } throws error

            charactersViewModel.getCharacters()

            val slot = slot<ViewState>()

            verifyOrder {
                observer.onChanged(Loading)
                observer.onChanged(capture(slot))
            }

            val errorReceived = slot.captured as LoadingError

            assertNotNull(errorReceived)
            assertEquals(errorReceived.message, errorReceived.message)

            coVerify { characterUseCase.getCharacters() }
        }
    }

    @Test
    fun `given name filter when getCharacters then return filtered characters`() {
        runBlocking {
            coEvery { characterUseCase.getCharacters() } returns flowOf(characters)

            charactersViewModel.getCharacters()
            charactersViewModel.nameSearch("alex")

            val slot = slot<ViewState>()

            verifyOrder {
                observer.onChanged(Loading)
                observer.onChanged(capture(slot))
            }

            val loaded = slot.captured as Loaded<List<Character>>

            assertNotNull(loaded)
            assertEquals(1, loaded.data.size)
            assertEquals("alex", loaded.data.first().name.toLowerCase())

            coVerify { characterUseCase.getCharacters() }
        }
    }

    @Test
    fun `given season filter when getCharacters then return filtered characters`() {
        runBlocking {
            coEvery { characterUseCase.getCharacters() } returns flowOf(characters)

            charactersViewModel.getCharacters()
            charactersViewModel.selectedSeason(3)

            val slot = slot<ViewState>()

            verifyOrder {
                observer.onChanged(Loading)
                observer.onChanged(capture(slot))
            }

            val loaded = slot.captured as Loaded<List<Character>>

            assertNotNull(loaded)
            assertEquals(2, loaded.data.size)

            coVerify { characterUseCase.getCharacters() }
        }
    }

    @Test
    fun `given name and season filter when getCharacters then return filtered characters`() {
        runBlocking {
            coEvery { characterUseCase.getCharacters() } returns flowOf(characters)

            charactersViewModel.getCharacters()
            charactersViewModel.nameSearch("alex")
            charactersViewModel.selectedSeason(3)

            val slot = slot<ViewState>()

            verifyOrder {
                observer.onChanged(Loading)
                observer.onChanged(capture(slot))
            }

            val loaded = slot.captured as Loaded<List<Character>>

            assertNotNull(loaded)
            assertEquals(0, loaded.data.size)

            coVerify { characterUseCase.getCharacters() }
        }
    }
}