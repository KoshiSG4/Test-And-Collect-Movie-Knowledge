package com.example.android.coursework2.data
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MoviesDao {

    @Query("Select * from movies")
    suspend fun getAll(): List<Movies>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(vararg movie: Movies)
    @Insert
    suspend fun insertAll(vararg movies: Movies)

    @Query("Select * from movies where UPPER(actors) LIKE :actor")
    suspend fun getMovieTitle(actor: String): List<Movies>

}