@file:Suppress("RemoveExplicitTypeArguments")

package io.mateam.playground.di.data

import io.mateam.playground2.data.dataSource.local.sharedPref.UserPreferences
import io.mateam.playground2.data.dataSource.local.sharedPref.UserSharedPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val preferencesModule = module {
    single<UserPreferences> { UserSharedPreferences(androidContext()) }
}

