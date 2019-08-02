package io.mateam.playground.presentation.utils

import android.os.Parcelable
import androidx.fragment.app.Fragment
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class NotNullParcelableArg<T : Parcelable>(private val argKey: String) : ReadOnlyProperty<Fragment, T> {
    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        return thisRef.arguments?.getParcelable(argKey)
            ?: throw IllegalArgumentException("unable to get arg for $argKey")
    }
}

class NotNullIntArg(private val argKey: String) : ReadOnlyProperty<Fragment, Int> {
    override fun getValue(thisRef: Fragment, property: KProperty<*>): Int {
        return thisRef.arguments?.getInt(argKey) ?: throw IllegalArgumentException("unable to get arg for $argKey")
    }
}

class NotNullLongArg(private val argKey: String) : ReadOnlyProperty<Fragment, Int> {
    override fun getValue(thisRef: Fragment, property: KProperty<*>): Int {
        return thisRef.arguments?.getInt(argKey) ?: throw IllegalArgumentException("unable to get arg for $argKey")
    }
}

class NotNullStringArg(private val argKey: String) : ReadOnlyProperty<Fragment, String> {
    override fun getValue(thisRef: Fragment, property: KProperty<*>): String {
        return thisRef.arguments?.getString(argKey) ?: throw IllegalArgumentException("unable to get arg for $argKey")
    }
}

class NotNullBooleanArg(private val argKey: String) : ReadOnlyProperty<Fragment, Boolean> {
    override fun getValue(thisRef: Fragment, property: KProperty<*>): Boolean {
        return thisRef.arguments?.getBoolean(argKey) ?: throw IllegalArgumentException("unable to get arg for $argKey")
    }
}