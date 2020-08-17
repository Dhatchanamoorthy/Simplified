package com.example.simplified.api

import androidx.lifecycle.LiveData
import com.example.simplified.common.network.ApiResponse
import com.example.simplified.models.Owner
import com.example.simplified.models.Response
import com.google.gson.JsonObject
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SimpleApi {

    @GET("repositories")
    fun getRepoList(): LiveData<ApiResponse<List<Response>>>

    @GET("repositories/{id}")
    fun getRepoDetail( @Path("id") id: String): LiveData<ApiResponse<Response>>

    @GET("users/{name}/followers")
    fun getRepoFollowers(@Path("name") name: String ): LiveData<ApiResponse<List<Owner>>>

    @GET("users/{name}/following{/other_user}")
    fun getRepoFollowing(@Path("name") name: String ): LiveData<ApiResponse<List<Owner>>>


}