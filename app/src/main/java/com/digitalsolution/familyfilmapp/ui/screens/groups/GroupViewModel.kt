package com.digitalsolution.familyfilmapp.ui.screens.groups

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digitalsolution.familyfilmapp.BaseUiState
import com.digitalsolution.familyfilmapp.exceptions.CustomException
import com.digitalsolution.familyfilmapp.exceptions.GroupException
import com.digitalsolution.familyfilmapp.model.local.Group
import com.digitalsolution.familyfilmapp.repositories.BackendRepository
import com.digitalsolution.familyfilmapp.repositories.LocalRepository
import com.digitalsolution.familyfilmapp.ui.screens.groups.states.GroupBackendState
import com.digitalsolution.familyfilmapp.ui.screens.groups.states.GroupUiState
import com.digitalsolution.familyfilmapp.utils.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(
    private val repository: BackendRepository,
    private val localRepository: LocalRepository,
    private val dispatcherProvider: DispatcherProvider,
) : ViewModel() {

    private val _state = MutableStateFlow(GroupBackendState())
    val state: StateFlow<GroupBackendState> = _state

    private val _groupUIState = MutableStateFlow(GroupUiState())
    val groupUIState: StateFlow<GroupUiState> = _groupUIState

    init {
        init()
        // getGroupForIndex(0)
    }

    private fun init() = viewModelScope.launch(dispatcherProvider.io()) {
        _state.showProgressIndicator(true)

        _state.update { state ->
            state.copy(
                groups = repository.getGroups().getOrElse {
                    Timber.e(it)
                    emptyList()
                },
                isLoading = false,
            )
        }
    }

    fun addGroup(groupName: String) = viewModelScope.launch(dispatcherProvider.io()) {
        _state.showProgressIndicator(true)

        repository.addGroups(groupName).fold(
            onSuccess = {
                init()
                _state.update { oldState ->
                    oldState.copy(
                        errorMessage = CustomException.GenericException("New group created!"),
                        isLoading = false,
                    )
                }
            },
            onFailure = {
                Timber.e(it)
                _state.update { oldState ->
                    oldState.copy(
                        errorMessage = GroupException.AddGroup(),
                        isLoading = false,
                    )
                }
            },
        )
    }

    fun deleteGroup(groupId: Int) = viewModelScope.launch(dispatcherProvider.io()) {
        _state.showProgressIndicator(true)

        try {
            repository.deleteGroup(groupId)
            init() // Recarga los grupos después de la eliminación
            _state.update { oldState ->
                oldState.copy(
                    errorMessage = CustomException.GenericException("Group deleted"),
                    isLoading = false,
                )
            }
        } catch (e: Exception) {
            Timber.e(e)
            _state.update { oldState ->
                oldState.copy(
                    errorMessage = GroupException.DeleteGroup(),
                    isLoading = false,
                )
            }
        }
    }

    fun getGroupForIndex(index: Int) = viewModelScope.launch(dispatcherProvider.io()) {
        if (index in _state.value.groups.indices) {
            val newSelectedGroup = _state.value.groups[index]
            _groupUIState.update { uiState ->
                uiState.copy(groupSelected = newSelectedGroup)
            }
            Timber.d("El grupo actualizado es este ${_groupUIState.value.groupSelected.name}")
        } else {
            Timber.d("El grupo actualizado es este ${_groupUIState.value.groupSelected.name}")
        }
    }

    fun tabChange(selectedGroup: Group) {
        _groupUIState.update { oldValue ->
            Timber.d("Compare users Id: ${selectedGroup.groupCreatorId} == ${localRepository.getUserId()}")
            oldValue.copy(
                deleteGroupButtonVisibility = mutableStateOf(
                    selectedGroup.groupCreatorId == localRepository.getUserId(),
                ),
            )
        }
    }
}

// TODO: Move this extension function
@Suppress("UNCHECKED_CAST")
fun <T : BaseUiState> MutableStateFlow<T>.showProgressIndicator(value: Boolean) {
    this.value = this.value.copyWithLoading(value) as T
}
