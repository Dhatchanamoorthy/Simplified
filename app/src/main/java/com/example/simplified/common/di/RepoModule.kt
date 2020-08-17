package com.example.simplified.common.di

import com.example.simplified.api.SimpleApi
import com.example.simplified.viewmodel.HomeViewModel
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import timber.log.Timber
import javax.inject.Named

@Module(includes = [(RepoViewModule::class)])
class RepoModule {

    @Provides
    @FeatureScope
    fun provideRepoApi(@Named("API_URLS") retrofit: Retrofit): SimpleApi {
        Timber.d("Preparing new instance for LoginApi(API_URLS)")
        return retrofit.create(SimpleApi::class.java)
    }


}