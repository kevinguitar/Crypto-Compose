package com.kevingt.cryptocompose.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThemeRepo @Inject constructor(@ApplicationContext context: Context) {

    private val pref = context.getSharedPreferences("theme", MODE_PRIVATE)
    private val editor get() = pref.edit()

    private val _isDarkThemeState = mutableStateOf(true)
    val isDarkThemeState: State<Boolean> get() = _isDarkThemeState

    fun initDarkTheme(isDarkTheme: Boolean) {
        if (KEY_DARK_THEME !in pref) {
            editor.putBoolean(KEY_DARK_THEME, isDarkTheme).apply()
            _isDarkThemeState.value = isDarkTheme
        } else {
            _isDarkThemeState.value = pref.getBoolean(KEY_DARK_THEME, true)
        }
    }

    fun setDarkTheme(isDarkTheme: Boolean) {
        editor.putBoolean(KEY_DARK_THEME, isDarkTheme).apply()
        _isDarkThemeState.value = isDarkTheme
    }
}

private const val KEY_DARK_THEME = "dark_theme"