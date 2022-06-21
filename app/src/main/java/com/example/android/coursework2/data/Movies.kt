package com.example.android.coursework2.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class Movies(
    @PrimaryKey

    @ColumnInfo(name ="title")
    val title: String,
    @ColumnInfo(name = "year")
    val year: String,
    @ColumnInfo(name = "rated")
    val rated: String,
    @ColumnInfo(name = "released")
    val released: String,
    @ColumnInfo(name = "runtime")
    val runtime: String,
    @ColumnInfo(name = "genre")
    val genre: String,
    @ColumnInfo(name = "director")
    val director: String,
    @ColumnInfo(name = "writer")
    val writer: String,
    @ColumnInfo(name = "actors")
    val actors: String,
    @ColumnInfo(name = "plot")
    val plot: String
)