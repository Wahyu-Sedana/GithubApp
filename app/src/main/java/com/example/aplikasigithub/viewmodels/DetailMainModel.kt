package com.example.aplikasigithub.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aplikasigithub.apis.ApiConfig
import com.example.aplikasigithub.helper.FavoriteUser
import com.example.aplikasigithub.helper.FavoriteUserDao
import com.example.aplikasigithub.helper.UserDatabase
import com.example.aplikasigithub.models.user.ResponseUsers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailMainModel(application: Application) : AndroidViewModel(application) {

    val detailUser = MutableLiveData<ResponseUsers>()

    private var userDao: FavoriteUserDao?
    private var userDB: UserDatabase?

    init {
        userDB = UserDatabase.getDatabase(application)
        userDao = userDB?.favoriteUserDao()
    }

    fun setUserDetail(username: String) {
        ApiConfig.apiInstance
            .getDetailUser(username)
            .enqueue(object : Callback<ResponseUsers> {
                override fun onResponse(
                    call: Call<ResponseUsers>,
                    response: Response<ResponseUsers>
                ) {
                    if (response.isSuccessful) {
                        detailUser.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ResponseUsers>, t: Throwable) {
                    Log.d("Failur", t.message.toString())
                }

            })
    }

    fun getDetailUser(): LiveData<ResponseUsers> {
        return detailUser
    }

    fun addToFavorite(username: String, id: Int, avatar_url: String, url: String) {
        CoroutineScope(Dispatchers.IO).launch {
            var user = FavoriteUser(
                username, id, avatar_url, url
            )
            userDao?.addToFavorite(user)
        }
    }

    suspend fun checkUser(id: Int) = userDao?.checkUser(id)

    fun removewUser(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeUser(id)
        }
    }

}