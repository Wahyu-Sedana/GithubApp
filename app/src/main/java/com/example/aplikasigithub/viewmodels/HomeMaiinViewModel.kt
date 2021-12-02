package com.example.aplikasigithub.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aplikasigithub.apis.ApiConfig
import com.example.aplikasigithub.models.follow.ResponseFollow
import com.example.aplikasigithub.models.searchdata.ResponseSearchData
import com.example.aplikasigithub.models.searchdata.SearchData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeMaiinViewModel: ViewModel() {
    val listUser = MutableLiveData<ArrayList<ResponseSearchData>>()
    val listFollower = MutableLiveData<ArrayList<ResponseFollow>>()
    val listFollowing = MutableLiveData<ArrayList<ResponseFollow>>()

    fun setSearchUser(username: String){
        ApiConfig.apiInstance
            .getUSer(username)
            .enqueue(object : Callback<SearchData> {
                override fun onResponse(call: Call<SearchData>, response: Response<SearchData>) {
                    if(response.isSuccessful){
                        listUser.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<SearchData>, t: Throwable) {
                    Log.d("Failur", t.message.toString())
                }

            })
    }


    fun setUserFollower(username: String){
        ApiConfig.apiInstance
            .getUserFollower(username)
            .enqueue(object : Callback<ArrayList<ResponseFollow>>{
                override fun onResponse(
                    call: Call<ArrayList<ResponseFollow>>,
                    response: Response<ArrayList<ResponseFollow>>
                ) {
                    if(response.isSuccessful){
                        listFollower.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<ResponseFollow>>, t: Throwable) {
                    Log.d("Failur", t.message.toString())
                }
            })
    }

    fun setUserFollowing(username: String){
        ApiConfig.apiInstance
            .getUserFollowing(username)
            .enqueue(object : Callback<ArrayList<ResponseFollow>>{
                override fun onResponse(
                    call: Call<ArrayList<ResponseFollow>>,
                    response: Response<ArrayList<ResponseFollow>>
                ) {
                    if(response.isSuccessful){
                        listFollowing.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<ResponseFollow>>, t: Throwable) {
                    Log.d("Failur", t.message.toString())
                }
            })
    }

    fun getUserFollower(): LiveData<ArrayList<ResponseFollow>>{
        return  listFollower
    }

    fun getUserFollowing(): LiveData<ArrayList<ResponseFollow>>{
        return  listFollowing
    }

    fun getSearchUser(): LiveData<ArrayList<ResponseSearchData>>{
        return listUser
    }

}