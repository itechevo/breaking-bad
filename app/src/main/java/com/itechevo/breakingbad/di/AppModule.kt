package com.itechevo.breakingbad.di

import com.itechevo.domain.provider.CoroutineContextProvider
import com.itechevo.breakingbad.ui.characters.CharactersViewModel
import com.itechevo.data.CharacterRepositoryImpl
import com.itechevo.data.source.BreakingBadApiService
import com.itechevo.data.source.RetrofitProvider
import com.itechevo.domain.CharacterInteractor
import com.itechevo.domain.repository.CharacterRepository
import com.itechevo.domain.usecase.CharacterUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel { CharactersViewModel(get()) }

    single { CoroutineContextProvider() }
}

val domainModule = module {

    single<CharacterUseCase> { CharacterInteractor(get(), get()) }
}

val dataModule = module {

    single<CharacterRepository> { CharacterRepositoryImpl(get()) }

    single<BreakingBadApiService> {
        RetrofitProvider.get(BreakingBadApiService.BASE_URL)
            .create(BreakingBadApiService::class.java)
    }
}