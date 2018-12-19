package com.b00sti.cleaneverything.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.b00sti.cleaneverything.ui.search.SearchViewModel
import com.b00sti.cleaneverything.viewmodel.GithubViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(searchViewModel: SearchViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: GithubViewModelFactory): ViewModelProvider.Factory
}