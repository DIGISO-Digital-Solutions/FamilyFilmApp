package com.digitalsolution.familyfilmapp.ui.screens.recommend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digitalsolution.familyfilmapp.repositories.BackendRepository
import com.digitalsolution.familyfilmapp.ui.screens.recommend.states.MovieUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RecommendViewModel @Inject constructor(
    private val repository: BackendRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(MovieUiState())
    val state: StateFlow<MovieUiState> = _state.asStateFlow().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = MovieUiState(),
    )

    init {
        viewModelScope.launch {
            _state.update { state ->
                state.copy(
                    movies = repository.getMovies().getOrElse {
                        Timber.e(it)
                        emptyList()
                    },
                )
            }
        }
    }
}
