package io.mateam.playground2.data.dataSource.local.sharedPref

import android.content.Context
import android.content.SharedPreferences
import io.mateam.playground2.data.utils.NullableStringPreference


interface UserPreferences {
    var loggedInUserId: String?
}

class UserSharedPreferences(context: Context) : UserPreferences {
    private val prefs: SharedPreferences by lazy {
        context.getSharedPreferences("io.mateam.playground", Context.MODE_PRIVATE)
    }

    override var loggedInUserId: String? by NullableStringPreference(prefs, PREF_KEY_LOGGED_IN_USER_ID, null)

    private companion object Const {
        const val PREF_KEY_LOGGED_IN_USER_ID = "io.mateam.playground.LOGGED_IN_USER_ID"

    }
}

