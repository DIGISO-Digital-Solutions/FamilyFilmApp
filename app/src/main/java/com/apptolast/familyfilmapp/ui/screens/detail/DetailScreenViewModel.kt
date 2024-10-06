package com.apptolast.familyfilmapp.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apptolast.familyfilmapp.repositories.BackendRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
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
class DetailScreenViewModel @Inject constructor(
//    private val watchListUseCase: WatchListUseCase,
//    private val seenListUseCase: SeenListUseCase,
    private val backendRepository: BackendRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailScreenUIState())
    val uiState: StateFlow<DetailScreenUIState> = _uiState.asStateFlow().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = DetailScreenUIState(),
    )

    init {
        viewModelScope.launch {
            awaitAll(
                async {
                    backendRepository.me().getOrNull()?.let { user ->
                        _uiState.update { it.copy(me = user) }
                    }
                },
                async { getGroupsToAddMovie() },
            )
        }
    }

    private fun getGroupsToAddMovie() = viewModelScope.launch {
        backendRepository.getGroupsUser().fold(
            onSuccess = { groups ->
                _uiState.update { it.copy(groups = groups) }
            },
            onFailure = { error ->
                Timber.e(error, "Error to get Groups of the users")
            },
        )
    }

    fun toWatchClicked(groupId: Int, movieId: Int) = viewModelScope.launch {
        backendRepository.addMovieToWatchList(groupId, movieId).fold(
            onSuccess = {
            },
            onFailure = {
            },
        )
    }

    fun watchedClicked(groupId: Int, movieId: Int) = viewModelScope.launch {
        backendRepository.addMovieToSeenList(groupId, movieId).fold(
            onSuccess = {
            },
            onFailure = {
            },
        )
    }

    fun showDialogGroups(dialogType: MovieDialogType) {
        _uiState.update { it.copy(showDialogGroups = dialogType) }
    }
}
