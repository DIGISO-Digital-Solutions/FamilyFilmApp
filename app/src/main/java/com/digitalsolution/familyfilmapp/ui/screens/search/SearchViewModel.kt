package com.digitalsolution.familyfilmapp.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digitalsolution.familyfilmapp.repositories.FilmRepository
import com.digitalsolution.familyfilmapp.ui.screens.recommend.FilmUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val fakeRepository: FilmRepository
) : ViewModel() {

    private val _state = MutableStateFlow(FilmUiState())
    val state: StateFlow<FilmUiState> = _state.asStateFlow().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = FilmUiState()
    )

    init {
        viewModelScope.launch {
            _state.update { st ->
                st.copy(films = fakeRepository.generateFakeFilmData(20))
            }
        }
    }

    fun setOnToSee(){

    }
    fun setOnSeen(){

    }
}
