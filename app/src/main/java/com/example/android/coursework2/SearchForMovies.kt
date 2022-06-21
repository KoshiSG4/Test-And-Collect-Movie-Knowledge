package com.example.android.coursework2

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.room.Room
import com.example.android.coursework2.data.Movies
import com.example.android.coursework2.data.MoviesDao
import com.example.android.coursework2.data.MoviesRoomDatabase
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL

class SearchForMovies : AppCompatActivity() {
    var url_string: String? = null
    lateinit var moviesDao: MoviesDao
    lateinit var movieDetails: Movies

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_for_movies)

        val retrieveMovie = findViewById<Button>(R.id.retrieve_btn)
        retrieveMovie.setOnClickListener {
            getMovie()
        }

        val saveRetrievedMovieData = findViewById<Button>(R.id.save_movie_to_DB)
        saveRetrievedMovieData.setOnClickListener {
            saveRetrievedMovieToDB(movieDetails)

        }
    }

    fun getMovie(){
        val movieTitle = findViewById<TextInputEditText>(R.id.text_input)
        val movieName = movieTitle!!.text.toString().trim()     //initializing userInput variable with user input
        if (movieName==""){
            return
        }
        url_string = "https://www.omdbapi.com/?t=$movieName&apikey=89d2beed"    //url to access web service

        var data: String = ""

        //start the fetching of data in the background
        runBlocking {
            withContext(Dispatchers.IO){
                var stringBuilder = StringBuilder("")
                val url = URL(url_string)
                val connection = url.openConnection() as HttpURLConnection
                val bufferReader: BufferedReader
                try{
                    bufferReader = BufferedReader(InputStreamReader(connection.inputStream))
                }
                catch(e: IOException){
                    e.printStackTrace()
                    return@withContext
                }

                var line = bufferReader.readLine()
                while(line != null){
                    stringBuilder.append(line)
                    line=bufferReader.readLine()
                }

                //pick up all the data
                data = parseJSON(stringBuilder)

            }
            val displayMovies = findViewById<EditText>(R.id.display_movie_details)
            displayMovies.setText(data)
            displayMovies.setBackgroundColor(Color.WHITE)



        }

    }

    @SuppressLint("SetTextI18n")
    fun parseJSON(stb: StringBuilder): String{
        //the full JSON returned by the web service
        val movieInfo = JSONObject(stb.toString())

        //extract the actual data
        val title = movieInfo["Title"] as String
        val year = movieInfo["Year"] as String
        val rated = movieInfo["Rated"] as String
        val released = movieInfo["Released"] as String
        val runtime = movieInfo["Runtime"] as String
        val genre = movieInfo["Genre"] as String
        val director = movieInfo["Director"] as String
        val writer = movieInfo["Writer"] as String
        val actors = movieInfo["Actors"] as String
        val plot = movieInfo["Plot"] as String

        //creating movie constructor
        movieDetails = Movies(title, year, rated, released, runtime, genre, director, writer, actors, plot)
        var all_info  =
            "Title: $title\nYear: $year\nRated: $rated\nReleased: $released\nRuntime: $runtime\nGenre: $genre\nDirector: $director\nWriter: $writer\nActors: $actors\n\nPlot: $plot"

        return all_info
    }

    fun saveRetrievedMovieToDB(movies: Movies){
        val db = Room.databaseBuilder(
            this, MoviesRoomDatabase::class.java,
            "movie_database"
        ).build()

        moviesDao = db.moviesDao()   //initializing moviesDao variable
        runBlocking {
            moviesDao.insertMovie(movies)   //inserting a new entity instance
        }
        val displayMovies = findViewById<EditText>(R.id.display_movie_details)
        displayMovies.append("\nSAVED")
    }
}