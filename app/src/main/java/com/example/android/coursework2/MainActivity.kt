package com.example.android.coursework2

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.android.coursework2.data.Movies
import com.example.android.coursework2.data.MoviesRoomDatabase
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val addMovies = findViewById<Button>(R.id.add_Movies)
        addMovies.setOnClickListener {
            addMoviesToDB()
        }

        val searchMovies = findViewById<Button>(R.id.search_Movies)
        searchMovies.setOnClickListener {
            val movieIntent = Intent(this,SearchForMovies::class.java)
            startActivity(movieIntent)
        }

        val searchActors = findViewById<Button>(R.id.search_Actors)
        searchActors.setOnClickListener {
            val actorsIntent = Intent(this,SearchForActors::class.java)
            startActivity(actorsIntent)
        }
        val userInput = findViewById<EditText>(R.id.input_for_web_search)
        val searchFromWeb = findViewById<Button>(R.id.retrieve_from_web_service)
        searchFromWeb.setOnClickListener {
            userInput.requestFocus()
            userInput.isFocusableInTouchMode = true
            userInput.setHint("Type movie name here").toString()
            userInput.setBackgroundColor(Color.rgb(71, 72, 79))
            userInput.setTextColor(Color.WHITE)
        }
    }

    private fun addMoviesToDB() {

        // create the database
        val db = Room.databaseBuilder(
            this, MoviesRoomDatabase::class.java,
            "movie_database"
        ).build()
        val movieDao = db.moviesDao()

        //fetching the data in the background
        runBlocking {
            println("runblocking....")
            launch {
                val user = Movies(

                    "The Shawshank Redemption",
                    "1994",
                    "R",
                    "14 Oct 1994",
                    "142 min",
                    "Drama",
                    "Frank Darabont",
                    "Stephen King, Frank Darabon",
                    "Tim Robbins, Morgan Freeman, Bob Gunton",
                    "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency."
                )
                val user2 = Movies(

                    "Batman: The Dark Knight Returns, Part 1",
                    "2012",
                    "PG-13",
                    "25 Sep 2012",
                    "76 min",
                    "Animation, Action, Crime, Drama, Thriller",
                    "Jay Oliva",
                    "Bob Kane (character created by: Batman), Frank Miller (comic book), Klaus Janson (comic book), Bob Goodman",
                    "Peter Weller, Ariel Winter, David Selby, Wade Williams",
                    "Batman has not been seen for ten years. A new breed of criminal ravages Gotham City, forcing 55-year-old Bruce Wayne back into the cape and cowl. But, does he still have what it takes to fight crime in a new era?"
                )
                val user3 = Movies(

                    "The Lord of the Rings: The Return of the King",
                    "2003",
                    "PG-13",
                    "17 Dec 2003",
                    "201 min",
                    "Action, Adventure, Drama",
                    "Peter Jackson",
                    "J.R.R. Tolkien, Fran Walsh, Philippa Boyens",
                    "Elijah Wood, Viggo Mortensen, Ian McKellen",
                    "Gandalf and Aragorn lead the World of Men against Sauron's army to draw his gaze from Frodo and Sam as they approach Mount Doom with the One Ring."
                )
                val user4 = Movies(

                    "Inception",
                    "2010",
                    "PG-13",
                    "16 Jul 2010",
                    "148 min",
                    "Action, Adventure, Sci-Fi",
                    "Christopher Nolan",
                    "Christopher Nolan",
                    "Leonardo DiCaprio, Joseph Gordon-Levitt, Elliot Page",
                    "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O., but his tragic past may doom the project and his team to disaster."
                )
                val user5 = Movies(

                    "The Matrix",
                    "1999",
                    "R",
                    "31 Mar 1999",
                    "136 min",
                    "Action, Sci-Fi",
                    "Lana Wachowski, Lilly Wachowski",
                    "Lilly Wachowski, Lana Wachowski",
                    "Keanu Reeves, Laurence Fishburne, Carrie-Anne Moss",
                    "When a beautiful stranger leads computer hacker Neo to a forbidding underworld, he discovers the shocking truth--the life he knows is the elaborate deception of an evil cyber-intelligence."
                )

//                movieDao.insertAll(user,user2,user3,user4,user5)
//                val movies: Flow<List<Movies>> = movieDao.getAll()
                val tv = findViewById<TextView>(R.id.tv)
                tv.setText("Successfully Added Movies to Database ")
                tv.setBackgroundColor(Color.rgb(71, 72, 79))
                tv.setTextColor(Color.WHITE)
            }
        }
    }

    private fun retrieveFromWeb(){

    }
}