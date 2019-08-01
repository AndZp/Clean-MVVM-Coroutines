package io.mateam.playground.presentation.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.mateam.playground.presentation.R

class MoviesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, MoviesActivity::class.java)
    }
}
