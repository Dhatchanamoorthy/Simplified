package com.example.simplified.common.di

import com.example.simplified.common.App
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module(includes = [NetworkModule::class, ViewModelModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideApplication(): App = App.app

    @Provides
    @Singleton
    fun providePicasso(app: App): Picasso = Picasso.Builder(app).build()

}