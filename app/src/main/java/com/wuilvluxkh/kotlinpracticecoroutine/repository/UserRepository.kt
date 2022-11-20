package com.wuilvluxkh.kotlinpracticecoroutine.repository

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wuilvluxkh.kotlinpracticecoroutine.api.UserAPI
import com.wuilvluxkh.kotlinpracticecoroutine.model.UserRequest
import com.wuilvluxkh.kotlinpracticecoroutine.model.UserResponse
import com.wuilvluxkh.kotlinpracticecoroutine.utils.NetworkResult
import javax.inject.Inject

class UserRepository @Inject constructor(private val userAPI: UserAPI) {

    private val _userResponseLiveData = MutableLiveData<NetworkResult<UserResponse>>()
    val userResponseLiveData : LiveData<NetworkResult<UserResponse>> get() = _userResponseLiveData

    suspend fun registerUser(userRequest: UserRequest){
        _userResponseLiveData.postValue(NetworkResult.Loading())
        val response = userAPI.signUp(userRequest)
        if (response.isSuccessful && response.body() != null){
            _userResponseLiveData.postValue(NetworkResult.Success(response.body()!!))
        }else if (response.errorBody() != null){
            _userResponseLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }else{
            _userResponseLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }

    suspend fun loginUser(userRequest: UserRequest){
        val response = userAPI.signIn(userRequest)
        if (response.isSuccessful){

        }else if (response.errorBody() != null){

        }else{

        }
    }

}