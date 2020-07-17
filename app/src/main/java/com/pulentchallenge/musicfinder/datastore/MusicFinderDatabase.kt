package com.pulentchallenge.musicfinder.datastore

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pulentchallenge.musicfinder.R
import com.pulentchallenge.musicfinder.datastore.dao.MusicTrackDao
import com.pulentchallenge.musicfinder.datastore.entity.MusicTrackEntity

@Database(entities = [MusicTrackEntity::class], version = 1, exportSchema = false)
abstract class MusicFinderDatabase : RoomDatabase() {
    abstract fun musicTrackDao(): MusicTrackDao

    companion object {
        @Volatile
        private var INSTANCE: MusicFinderDatabase? = null

        fun getDatabase(context: Context): MusicFinderDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MusicFinderDatabase::class.java,
                    context.getString(R.string.database_name)
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }

}