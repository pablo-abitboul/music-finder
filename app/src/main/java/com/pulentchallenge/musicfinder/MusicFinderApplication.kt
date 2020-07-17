package com.pulentchallenge.musicfinder

import android.app.Application
import com.pulentchallenge.musicfinder.datastore.databaseModule
import com.pulentchallenge.musicfinder.network.networkModule
import com.pulentchallenge.musicfinder.repository.repositoryModule
import com.pulentchallenge.musicfinder.viewmodel.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MusicFinderApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MusicFinderApplication)
            modules(
                listOf(
                    networkModule,
                    repositoryModule,
                    databaseModule,
                    viewModelModule
                )
            )
        }
    }
}