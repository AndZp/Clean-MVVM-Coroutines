package io.mateam.playground2.data.utils

import android.annotation.SuppressLint
import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

@SuppressLint("ApplySharedPref")
class IntPreference(
        private val preferences: SharedPreferences,
        private val name: String,
        private val defaultValue: Int = 0
) : ReadWriteProperty<Any, Int> {

    override fun getValue(thisRef: Any, property: KProperty<*>): Int {
        return preferences.getInt(name, defaultValue)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Int) {
        preferences.edit().putInt(name, value).commit()
    }
}

@SuppressLint("ApplySharedPref")
class LongPreference(
        private val preferences: SharedPreferences,
        private val name: String,
        private val defaultValue: Long = 0
) : ReadWriteProperty<Any, Long> {

    override fun getValue(thisRef: Any, property: KProperty<*>): Long {
        return preferences.getLong(name, defaultValue)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Long) {
        preferences.edit().putLong(name, value).commit()
    }
}

@SuppressLint("ApplySharedPref")
class FloatPreference(
        private val preferences: SharedPreferences,
        private val name: String,
        private val defaultValue: Float = 0f
) : ReadWriteProperty<Any, Float> {

    override fun getValue(thisRef: Any, property: KProperty<*>): Float {
        return preferences.getFloat(name, defaultValue)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Float) {
        preferences.edit().putFloat(name, value).commit()
    }
}

@SuppressLint("ApplySharedPref")
class BooleanPreference(
        private val preferences: SharedPreferences,
        private val name: String,
        private val defaultValue: Boolean = false
) : ReadWriteProperty<Any, Boolean> {

    override fun getValue(thisRef: Any, property: KProperty<*>): Boolean {
        return preferences.getBoolean(name, defaultValue)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Boolean) {
        preferences.edit().putBoolean(name, value).commit()
    }
}

@SuppressLint("ApplySharedPref")
class StringPreference(
    private val preferences: SharedPreferences,
    private val name: String,
    private val defaultValue: String
) : ReadWriteProperty<Any, String> {

    override fun getValue(thisRef: Any, property: KProperty<*>): String {
        return preferences.getString(name, defaultValue) ?: defaultValue
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: String) {
        preferences.edit().putString(name, value).commit()
    }
}


@SuppressLint("ApplySharedPref")
class NullableStringPreference(
    private val preferences: SharedPreferences,
    private val name: String,
    private val defaultValue: String?
) : ReadWriteProperty<Any, String?> {

    override fun getValue(thisRef: Any, property: KProperty<*>): String? {
        return preferences.getString(name, defaultValue) ?: defaultValue
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: String?) {
        preferences.edit().putString(name, value).commit()
    }
}
