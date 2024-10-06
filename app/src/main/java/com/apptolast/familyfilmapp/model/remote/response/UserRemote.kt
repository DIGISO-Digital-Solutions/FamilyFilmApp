package com.apptolast.familyfilmapp.model.remote.response

import com.apptolast.familyfilmapp.model.local.GroupMovieTuple
import com.apptolast.familyfilmapp.model.local.User
import com.google.gson.annotations.SerializedName

data class UserRemote(

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("email")
    val email: String? = null,

    @SerializedName("language")
    val language: String? = null,

    @SerializedName("role")
    val provider: String? = null,

    @SerializedName("movies_watch")
    val moviesWatch: List<GroupMovieTupleRemote>? = null,

    @SerializedName("movies_watched")
    val moviesWatchedList: List<GroupMovieTupleRemote>? = null,
)

data class GroupMovieTupleRemote(
    @SerializedName("id_group")
    val groupId: Long,
    @SerializedName("id_movie")
    val movieId: Long,
)


fun UserRemote.toDomain() = User(
    id = id ?: -1,
    email = email ?: "",
    language = language ?: "es",
    provider = "",
    moviesWatch = moviesWatch?.map { it.toDomain() } ?: emptyList(),
    moviesWatchList = moviesWatchedList?.map { it.toDomain() } ?: emptyList(),
)

fun GroupMovieTupleRemote.toDomain() = GroupMovieTuple(
    groupId = groupId,
    movieId = movieId,
)
