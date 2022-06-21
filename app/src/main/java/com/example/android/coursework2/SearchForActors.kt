package com.example.android.coursework2


import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.room.Room
import com.example.android.coursework2.data.Movies
import com.example.android.coursework2.data.MoviesDao
import com.example.android.coursework2.data.MoviesRoomDatabase
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SearchForActors : AppCompatActivity() {
    lateinit var moviesDao: MoviesDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_for_actors)

        val searchActorsAndMovies = findViewById<Button>(R.id.search)
        searchActorsAndMovies.setOnClickListener {
            searchForMovies()
        }
    }

    fun searchForMovies(){
        //accessing database
        val db = Room.databaseBuilder(
            this, MoviesRoomDatabase::class.java,
            "movie_database"
        ).build()

        //initializing moviesDao variable
        moviesDao = db.moviesDao()

        val actorName = findViewById<EditText>(R.id.type_actors_name)
        val userInput = actorName.text.toString()   //initializing userInput variable with user input
        if (userInput==""){
            return
        }
        val actor = "%$userInput%"      //changing user input the way it should be in order to match the SQL Query in MoviesDao
        runBlocking {
            launch {
                val retrievedMovies: List<Movies> = moviesDao.getMovieTitle(actor.uppercase())  //assigning retrieved data to a list
                val movieNames = findViewById<EditText>(R.id.display_result)
                movieNames.setText("Movies: \n")
                movieNames.setTextColor(Color.rgb(9, 16, 59))
                movieNames.setBackgroundColor(Color.WHITE)

                //display retrieved movies
                for (i in retrievedMovies){
                    movieNames.append("${i.title} \n")
                }
            }
        }
    }
}