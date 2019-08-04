package io.mateam.playground

import android.app.Application
import com.facebook.stetho.Stetho
import io.mateam.playground.di.data.localDataSourceModule
import io.mateam.playground.di.data.preferencesModule
import io.mateam.playground.di.data.remoteDataSourceModule
import io.mateam.playground.di.data.reposModule
import io.mateam.playground.di.domain.managersModule
import io.mateam.playground.di.domain.useCasesModule
import io.mateam.playground.di.presentation.popularMovies.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initLogs()
        initStetho()
        initDi()
    }

    private fun initLogs() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initStetho() {
        Stetho.initializeWithDefaults(this)
    }

    private fun initDi() {
        startKoin {
            androidContext(applicationContext)
            modules(
                listOf(
                    preferencesModule,
                    remoteDataSourceModule,
                    localDataSourceModule,
                    reposModule,
                    managersModule,
                    useCasesModule,
                    viewModelsModule
                )
            )
        }
    }
}