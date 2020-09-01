package com.itechevo.data

import com.itechevo.data.mapper.toCharacter
import com.itechevo.data.source.BreakingBadApiService
import com.itechevo.domain.repository.CharacterRepository
import com.itechevo.domain.model.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CharacterRepositoryImpl(private val breakingBadApiService: BreakingBadApiService) :
    CharacterRepository {

    private var cache: List<Character>? = null

    override suspend fun getCharacters(): Flow<List<Character>> = flow {
        cache?.let {
            emit(it)
        }

       breakingBadApiService.getCharacters().map { it.toCharacter() }.let {
           cache = it
           emit(it)
       }
    }
}