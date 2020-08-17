package com.example.simplified.common.di

import com.example.simplified.MainActivity
import com.example.simplified.ui.home.RepoListFragment
import com.example.simplified.ui.home.RepoDetailActivity
import dagger.Component


@FeatureScope
@Component(
    modules = [RepoModule::class], dependencies = [(AppComponent::class)])
interface RepoComponent {
    @Component.Builder
    interface Builder {
        fun appComponent(appComponent: AppComponent): Builder

        fun build(): RepoComponent
    }

    fun inject(activity: MainActivity)
    fun inject(activity: RepoDetailActivity)
    fun inject(fragment: RepoListFragment)

}