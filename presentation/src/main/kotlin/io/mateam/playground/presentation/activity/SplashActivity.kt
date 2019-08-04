package io.mateam.playground.presentation.activity

import android.animation.Animator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.mateam.playground.presentation.R
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initAnimationView()
    }

    private fun initAnimationView() {
        splash_lottie_background.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationEnd(animation: Animator?) {
                startMoviesActivity()
            }

            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }

            override fun onAnimationCancel(animation: Animator?) {
            }
        })
    }

    private fun startMoviesActivity() {
        startActivity(MoviesActivity.getIntent(applicationContext))
    }
}
