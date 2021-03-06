package com.example.simplified.common.di

import com.example.simplified.common.App
import com.squareup.picasso.Picasso
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class)])
interface AppComponent {

    fun getApp():App

    @Named("API_URLS")
    fun getRetrofit(): Retrofit

    fun getPicasso(): Picasso

    @Component.Builder
    interface Builder{
        fun build(): AppComponent
    }
}