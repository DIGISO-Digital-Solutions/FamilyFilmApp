package com.apptolast.familyfilmapp.network

import com.apptolast.familyfilmapp.model.remote.request.AddGroupBody
import com.apptolast.familyfilmapp.model.remote.request.AddMemberBody
import com.apptolast.familyfilmapp.model.remote.request.AddMovieWatchListBody
import com.apptolast.familyfilmapp.model.remote.request.RemoveMemberBody
import com.apptolast.familyfilmapp.model.remote.request.UpdateGroupNameBody
import com.apptolast.familyfilmapp.model.remote.response.AddGroupRemote
import com.apptolast.familyfilmapp.model.remote.response.AddMemberRemote
import com.apptolast.familyfilmapp.model.remote.response.GenreRemote
import com.apptolast.familyfilmapp.model.remote.response.GroupRemote
import com.apptolast.familyfilmapp.model.remote.response.MovieRemote
import com.apptolast.familyfilmapp.model.remote.response.UpdateGroupRemote
import com.apptolast.familyfilmapp.model.remote.response.UserRemote
import com.apptolast.familyfilmapp.network.ApiRoutesParams.GROUP_ID_PARAM
import com.apptolast.familyfilmapp.network.ApiRoutesParams.LANGUAGE
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface BackendApi {

    @GET(ApiRoutes.ME)
    suspend fun me(): UserRemote

    @POST(ApiRoutes.USER_CREATE)
    suspend fun createUser(): UserRemote

    @GET(ApiRoutes.MOVIES)
    suspend fun getMovies(): List<MovieRemote>

    @GET(ApiRoutes.GROUPS)
    suspend fun getGroups(): List<GroupRemote>

    @POST(ApiRoutes.CREATE_GROUP)
    suspend fun addGroups(@Body addGroupBody: AddGroupBody): AddGroupRemote

    @DELETE(ApiRoutes.REMOVE_GROUP)
    suspend fun deleteGroup(@Path(GROUP_ID_PARAM) groupId: Int, @Path(LANGUAGE) idiom: String)

    @PATCH(ApiRoutes.EDIT_GROUP_NAME)
    suspend fun updateGroupName(
        @Path(GROUP_ID_PARAM) groupId: Int,
        @Path(LANGUAGE) idiom: String,
        @Body updateGroupNameBody: UpdateGroupNameBody,
    ): UpdateGroupRemote

    @PATCH(ApiRoutes.ADD_MEMBER)
    suspend fun addMemberGroup(
        @Path(GROUP_ID_PARAM) groupId: Int,
        @Body addMemberBody: AddMemberBody,
    ): AddMemberRemote

    @PATCH(ApiRoutes.REMOVE_MEMBER_FROM_GROUP)
    suspend fun removeMemberFromGroup(@Path(GROUP_ID_PARAM) groupId: Int, @Body removeMemberBody: RemoveMemberBody)

    @PATCH(ApiRoutes.ADD_MOVIE_TO_WATCHLIST)
    suspend fun addMovieToWatchList(
        @Path(GROUP_ID_PARAM) groupId: Int,
        @Body addMovieWatchListBody: AddMovieWatchListBody,
    )

    @PATCH(ApiRoutes.ADD_MOVIE_TO_SEEN)
    suspend fun addMovieToSeenList(
        @Path(GROUP_ID_PARAM) groupId: Int,
        @Body addMovieSeenListBody: AddMovieWatchListBody,
    )

    @GET(ApiRoutes.GENRES)
    suspend fun getGenres(): List<GenreRemote>
}
