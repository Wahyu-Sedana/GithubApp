package com.example.aplikasigithub.helper

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "favorite_user")
data class FavoriteUser(
    val login: String,
    @PrimaryKey
    val id: Int,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    val url: String
) : Serializable
