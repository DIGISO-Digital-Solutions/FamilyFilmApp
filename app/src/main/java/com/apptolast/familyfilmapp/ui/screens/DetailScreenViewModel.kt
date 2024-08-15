package com.apptolast.familyfilmapp.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apptolast.familyfilmapp.repositories.BackendRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    private val watchListUseCase: WatchListUseCase,
    private val seenListUseCase: SeenListUseCase,
    private val backendRepository: BackendRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailScreenUIState())
    val uiState: StateFlow<DetailScreenUIState> = _uiState.asStateFlow().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = DetailScreenUIState(),
    )

    init {
        getGroupsToAddMovie()
    }

    fun addMovieToWatchList(groupId: Int, movieId: Int) = viewModelScope.launch {
        watchListUseCase(groupId to movieId).collectLatest { newState ->
            _uiState.update {
                newState
            }
        }
    }

    fun getGroupsToAddMovie() = viewModelScope.launch {
        backendRepository.getGroupsUser().fold(
            onSuccess = { groups ->
                _uiState.update { oldState ->
                    oldState.copy(
                        groups = groups,
                    )
                }
            },
            onFailure = {
                Timber.d("Error to get Groups of the users", "$it")
            },
        )
    }

    fun addMovieToSeenList(groupId: Int, movieId: Int) = viewModelScope.launch {
        seenListUseCase(groupId to movieId).collectLatest { newState ->
            _uiState.update {
                newState
            }
        }
    }
}
