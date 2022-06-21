package com.example.android.coursework2.data


import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Movies::class], version=1)
abstract class MoviesRoomDatabase: RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}