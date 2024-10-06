package com.apptolast.familyfilmapp.ui.screens.detail

import com.apptolast.familyfilmapp.BaseUiState
import com.apptolast.familyfilmapp.exceptions.CustomException
import com.apptolast.familyfilmapp.model.local.Group
import com.apptolast.familyfilmapp.model.local.User

data class DetailScreenUIState(
    val me: User,
    val groups: List<Group>,
    var showDialogGroups: MovieDialogType,
//    val successMovieToWatchList: String,
//    val successMovieToViewList: String,
//    var checkForSeenToSee: Boolean,
    override val isLoading: Boolean,
    override val errorMessage: CustomException?,
) : BaseUiState {

    constructor() : this(
        me = User(),
//        successMovieToWatchList = "",
//        successMovieToViewList = "",
        showDialogGroups = MovieDialogType.None,
//        checkForSeenToSee = false,
        groups = emptyList(),
        isLoading = false,
        errorMessage = null,
    )

    override fun copyWithLoading(isLoading: Boolean): BaseUiState {
        TODO("Not yet implemented")
    }
}


enum class MovieDialogType {
    Watched,
    Unwatched,
    None,
}
