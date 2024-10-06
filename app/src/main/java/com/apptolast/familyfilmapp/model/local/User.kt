package com.apptolast.familyfilmapp.model.local

data class User(
    val id: Int,
    val email: String,
    val language: String,
    val provider: String,
    val moviesWatch: List<GroupMovieTuple>,
    val moviesWatchList: List<GroupMovieTuple>,
) {
    constructor() : this(
        id = -1,
        email = "",
        language = "",
        provider = "",
        moviesWatch = emptyList<GroupMovieTuple>(),
        moviesWatchList = emptyList<GroupMovieTuple>(),
    )
}

data class GroupMovieTuple(
    val groupId: Long,
    val movieId: Long,
)

