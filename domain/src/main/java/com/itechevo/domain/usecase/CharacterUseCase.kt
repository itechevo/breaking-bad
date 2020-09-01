package com.itechevo.domain.usecase

import com.itechevo.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterUseCase {

    suspend fun getCharacters(): Flow<List<Character>>
}