package com.itechevo.breakingbad.ui.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itechevo.breakingbad.ui.model.LoadingError
import com.itechevo.breakingbad.ui.model.Loaded
import com.itechevo.breakingbad.ui.model.Loading
import com.itechevo.breakingbad.ui.model.ViewState
import com.itechevo.domain.model.Character
import com.itechevo.domain.usecase.CharacterUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

class CharactersViewModel(private val characterUseCase: CharacterUseCase) : ViewModel() {

    private val _viewState = MutableLiveData<ViewState>()

    val viewState: LiveData<ViewState> = _viewState

    var searchQuery: String = ""

    var selectedSeasons = mutableListOf<Int>()

    private lateinit var characters: List<Character>

    fun getCharacters() {
        viewModelScope.launch {
            _viewState.value = Loading
            try {
                characterUseCase.getCharacters().collect {
                    characters = it
                    handleResult()
                }
            } catch (e: Exception) {
                _viewState.value = LoadingError(e.message)
            }
        }
    }

    fun selectedSeason(season: Int) {
        if (selectedSeasons.contains(season)) {
            selectedSeasons.remove(season)
        } else {
            selectedSeasons.add(season)
        }

        handleResult()
    }
    
    fun nameSearch(query: String) {
        searchQuery = query
        handleResult()
    }

    private fun handleResult() {
        if (selectedSeasons.isEmpty()) {
            if (searchQuery.isEmpty()) {
                _viewState.value = Loaded(characters)
            } else {
                characters?.filter { it.name.toLowerCase(Locale.ROOT).contains(searchQuery) }.let {
                    _viewState.value = Loaded(it)
                }
            }
        } else {
            characters?.filter { it.appearance.any { selectedSeasons.contains(it) } }?.let {
                if (searchQuery.isEmpty()) {
                    _viewState.value = Loaded(it)
                } else {
                    it.filter { it.name.toLowerCase(Locale.ROOT).contains(searchQuery) }.let {
                        _viewState.value = Loaded(it)
                    }
                }
            }
        }
    }
}