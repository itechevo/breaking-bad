package com.itechevo.domain

import com.itechevo.domain.model.Character
import com.itechevo.domain.provider.CoroutineContextProvider
import com.itechevo.domain.repository.CharacterRepository
import com.itechevo.domain.usecase.CharacterUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class CharacterInteractor(
        private val characterRepository: CharacterRepository,
        private val coroutineContextProvider: CoroutineContextProvider
) : CharacterUseCase {

    override suspend fun getCharacters(): Flow<List<Character>> =
            characterRepository.getCharacters().flowOn(coroutineContextProvider.IO)
}