package com.apptolast.familyfilmapp.ui.screens

import com.apptolast.familyfilmapp.BaseUiState
import com.apptolast.familyfilmapp.exceptions.CustomException
import com.apptolast.familyfilmapp.model.local.Group

data class DetailScreenUIState(
    val successMovieToWatchList: String,
    val successMovieToViewList: String,
    val groups: List<Group>,
    override val isLoading: Boolean,
    override val errorMessage: CustomException?,

) : BaseUiState {

    constructor() : this(
        successMovieToWatchList = "",
        successMovieToViewList = "",
        groups = emptyList(),
        isLoading = false,
        errorMessage = null,
    )

    override fun copyWithLoading(isLoading: Boolean): BaseUiState {
        TODO("Not yet implemented")
    }
}
