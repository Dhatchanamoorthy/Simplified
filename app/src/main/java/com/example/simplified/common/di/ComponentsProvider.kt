package com.example.simplified.common.di

import com.example.simplified.common.App

class ComponentsProvider {

    companion object {
        fun getComponent() = DaggerRepoComponent.builder().appComponent(App.appComponent).build()
    }
}