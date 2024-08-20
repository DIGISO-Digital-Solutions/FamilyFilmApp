package com.apptolast.familyfilmapp.ui.screens

import com.apptolast.familyfilmapp.BaseUiState
import com.apptolast.familyfilmapp.exceptions.CustomException
import com.apptolast.familyfilmapp.model.local.Group

data class DetailScreenUIState(
    val successMovieToWatchList: String,
    val successMovieToViewList: String,
    var showDialogGroups: Boolean,
    var checkforSeenToSee: Boolean,
    val groups: List<Group>,
    override val isLoading: Boolean,
    override val errorMessage: CustomException?,

) : BaseUiState {

    constructor() : this(
        successMovieToWatchList = "",
        successMovieToViewList = "",
        showDialogGroups = false,
        checkforSeenToSee = false,
        groups = emptyList(),
        isLoading = false,
        errorMessage = null,
    )

    override fun copyWithLoading(isLoading: Boolean): BaseUiState {
        TODO("Not yet implemented")
    }
}
