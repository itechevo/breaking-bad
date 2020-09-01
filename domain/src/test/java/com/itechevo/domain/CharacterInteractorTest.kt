package com.itechevo.domain

import com.itechevo.domain.helper.TestCoroutineContextProvider
import com.itechevo.domain.model.Character
import com.itechevo.domain.repository.CharacterRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class CharacterInteractorTest {

    private val characterRepositoryImpl = mockk<CharacterRepository>()

    private val characterInteractor =
        CharacterInteractor(characterRepositoryImpl, TestCoroutineContextProvider())

    private val apiTestCharacters = listOf(mockk<Character>(), mockk(), mockk())

    @Test
    fun `given has characters when getCharacters then return characters`() {
        runBlocking {

            //given
            coEvery { characterRepositoryImpl.getCharacters() } returns flowOf(apiTestCharacters)

            //when
            val characters = characterInteractor.getCharacters().toList()

            //then
            coVerify { characterRepositoryImpl.getCharacters() }
            assertEquals(characters.size, 1)
            assertEquals(characters.first().size, 3)
        }
    }
}