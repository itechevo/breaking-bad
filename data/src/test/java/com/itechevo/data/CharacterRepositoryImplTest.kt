package com.itechevo.data

import com.itechevo.data.model.CharacterData
import com.itechevo.data.source.BreakingBadApiService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class CharacterRepositoryImplTest {

    private val breakingBadApiService = mockk<BreakingBadApiService>()

    private val characterRepositoryImpl = CharacterRepositoryImpl(breakingBadApiService)

    private val apiTestCharacters =
        listOf(
            CharacterData(1, "", "", emptyList(), "", "", "", emptyList(), "", "", emptyList()),
            CharacterData(2, "", "", emptyList(), "", "", "", emptyList(), "", "", emptyList()),
            CharacterData(3, "", "", emptyList(), "", "", "", emptyList(), "", "", emptyList())
        )

    @Test
    fun `given has characters when getCharacters then return characters`() {
        runBlocking {

            //given
            coEvery { breakingBadApiService.getCharacters() } returns apiTestCharacters

            //when
            val characters = characterRepositoryImpl.getCharacters().toList()

            //then
            coVerify { breakingBadApiService.getCharacters() }
            assertEquals(characters.size, 1)
            assertEquals(characters.first().size, 3)
        }
    }

    @Test
    fun `given cached characters when getCharacters then return cached characters before API call`() {
        runBlocking {

            val firstApiTestCharacters = listOf(
                CharacterData(1, "", "", emptyList(), "", "", "", emptyList(), "", "", emptyList()),
                CharacterData(2, "", "", emptyList(), "", "", "", emptyList(), "", "", emptyList()),
            )

            //given
            coEvery { breakingBadApiService.getCharacters() } returns firstApiTestCharacters
            val firstCharacters =
                characterRepositoryImpl.getCharacters().toList() //This will cache the characters

            coVerify { breakingBadApiService.getCharacters() }
            assertEquals(1, firstCharacters.size)
            assertEquals(2, firstCharacters.first().size)

            //given cached
            coEvery { breakingBadApiService.getCharacters() } returns apiTestCharacters

            //when
            val characters = characterRepositoryImpl.getCharacters().toList()

            //then
            assertEquals(2, characters.size)
            assertEquals(2, characters.first().size)
            assertEquals(3, characters[1].size)
        }
    }
}