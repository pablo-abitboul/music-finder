package com.pulentchallenge.musicfinder.repository

import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    single { SearchRepository(get(), get()) } bind SearchRepository::class
}