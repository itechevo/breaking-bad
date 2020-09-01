package com.itechevo.domain.repository

import com.itechevo.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    suspend fun getCharacters(): Flow<List<Character>>
}