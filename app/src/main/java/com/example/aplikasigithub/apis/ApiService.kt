package com.example.aplikasigithub.apis

import com.example.aplikasigithub.models.follow.ResponseFollow
import com.example.aplikasigithub.models.searchdata.SearchData
import com.example.aplikasigithub.models.user.ResponseUsers
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getUSer(
        @Query("q") username: String
    ):Call<SearchData>


    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<ResponseUsers>

    @GET("users/{username}/followers")
    fun getUserFollower(
        @Path("username") username: String
    ): Call<ArrayList<ResponseFollow>>

    @GET("users/{username}/following")
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<ArrayList<ResponseFollow>>
}