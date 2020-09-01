package com.itechevo.data.source

import com.itechevo.data.model.CharacterData
import retrofit2.http.GET

interface BreakingBadApiService {

    @GET("characters")
    suspend fun getCharacters(): List<CharacterData>

    companion object {
        const val BASE_URL = "https://breakingbadapi.com/api/"
    }
}