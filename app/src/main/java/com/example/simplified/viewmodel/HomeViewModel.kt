package com.example.simplified.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.simplified.api.SimpleApi
import com.example.simplified.common.network.ApiResponse
import com.example.simplified.common.network.NetworkOnlyResource
import com.example.simplified.common.network.Resource
import com.example.simplified.models.Owner
import com.example.simplified.models.Response
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val simpleApi: SimpleApi) : ViewModel() {

    fun getRepoList(): LiveData<Resource<List<Response>>> {
        return object : NetworkOnlyResource<List<Response>>() {
            override fun createCall(): LiveData<ApiResponse<List<Response>>> {
                return simpleApi.getRepoList()
            }
        }.asLiveData()
    }

    fun getRepoDetails(id: String): LiveData<Resource<Response>> {
        return object : NetworkOnlyResource<Response>() {
            override fun createCall(): LiveData<ApiResponse<Response>> {
                return simpleApi.getRepoDetail(id)
            }
        }.asLiveData()
    }

    fun getRepoFollowers(name: String): LiveData<Resource<List<Owner>>> {
        return object : NetworkOnlyResource<List<Owner>>() {
            override fun createCall(): LiveData<ApiResponse<List<Owner>>> {
                return simpleApi.getRepoFollowers(name)
            }
        }.asLiveData()
    }

    fun getRepoFollowing(name: String): LiveData<Resource<List<Owner>>> {
        return object : NetworkOnlyResource<List<Owner>>() {
            override fun createCall(): LiveData<ApiResponse<List<Owner>>> {
                return simpleApi.getRepoFollowing(name)
            }
        }.asLiveData()
    }
}