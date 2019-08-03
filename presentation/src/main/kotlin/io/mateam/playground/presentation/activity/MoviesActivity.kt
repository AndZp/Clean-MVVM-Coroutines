package io.mateam.playground.presentation.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import io.mateam.playground.presentation.R
import kotlinx.android.synthetic.main.activity_movies.*

class MoviesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)
        initBottomNavigation()
    }

    private fun initBottomNavigation() {
        navigation.setupWithNavController(Navigation.findNavController(this, R.id.nav_host_fragment))
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, MoviesActivity::class.java)
    }
}
