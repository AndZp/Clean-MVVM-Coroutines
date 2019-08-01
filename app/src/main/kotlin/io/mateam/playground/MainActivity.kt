package io.mateam.playground

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.mateam.playground.presentation.activity.MoviesActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startMoviesActivity()
    }

    private fun startMoviesActivity() {
        //Splash loading TBD
        GlobalScope.launch {
            delay(2000)
            startActivity(MoviesActivity.getIntent(applicationContext))
        }
    }
}
