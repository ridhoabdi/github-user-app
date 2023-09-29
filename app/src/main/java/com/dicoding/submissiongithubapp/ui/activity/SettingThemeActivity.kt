package com.dicoding.submissiongithubapp.ui.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.dicoding.submissiongithubapp.R
import com.dicoding.submissiongithubapp.datastore.SettingPreferences
import com.dicoding.submissiongithubapp.ui.viewmodel.Theme
import com.dicoding.submissiongithubapp.ui.viewmodel.ThemeViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingThemeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_theme)

        val switchTheme = findViewById<Switch>(R.id.switch_theme)
        val pref = SettingPreferences.getInstance(dataStore)
        val mainViewModel =
            ViewModelProvider(this, Theme(pref)).get(ThemeViewModel::class.java)

        mainViewModel.getThemeSetting().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            mainViewModel.saveThemeSetting(isChecked)
        }
    }
}