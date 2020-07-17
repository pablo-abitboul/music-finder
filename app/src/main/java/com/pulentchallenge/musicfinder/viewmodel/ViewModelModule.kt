package com.pulentchallenge.musicfinder.viewmodel

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

val viewModelModule: Module = module {
    viewModel { SearchViewModel(get()) } bind SearchViewModel::class
}