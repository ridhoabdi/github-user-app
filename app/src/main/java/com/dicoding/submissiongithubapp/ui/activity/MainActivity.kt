package com.dicoding.submissiongithubapp.ui.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submissiongithubapp.R
import com.dicoding.submissiongithubapp.data.remote.response.User
import com.dicoding.submissiongithubapp.databinding.ActivityMainBinding
import com.dicoding.submissiongithubapp.datastore.SettingPreferences
import com.dicoding.submissiongithubapp.ui.adapter.ListUserAdapter
import com.dicoding.submissiongithubapp.ui.viewmodel.Theme
import com.dicoding.submissiongithubapp.ui.viewmodel.ThemeViewModel
import com.dicoding.submissiongithubapp.ui.viewmodel.UserViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var adapter: ListUserAdapter
    private lateinit var themeViewModel: ThemeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()
        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                intent = Intent(this@MainActivity, DetailUserActivity::class.java)
                intent.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                intent.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                startActivity(intent)
            }
        })

        binding.apply {
            rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUser.setHasFixedSize(true)
            rvUser.adapter = adapter
            find.setOnClickListener {
                search()
            }
            txtFind.setOnKeyListener { _, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    search()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(UserViewModel::class.java)
        viewModel.get().observe(this) {
            if (it != null) {
                adapter.setList(it)
                showLoading(false)
            }
        }

        val pref = SettingPreferences.getInstance(dataStore)
        themeViewModel = ViewModelProvider(this, Theme(pref))[ThemeViewModel::class.java]
        ChangeTheme()
    }

    // search username
    private fun search() {
        binding.apply {
            val query = txtFind.text.toString()
            if (query.isEmpty()) return
            showLoading(true)
            viewModel.setSearch(query)
        }

    }

    // progressbar
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // run pilihan menu aplikasi github
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.setting -> {
                val menuGithub = Intent(this, SettingThemeActivity::class.java)
                startActivity(menuGithub)
                return true
            }

            R.id.favorite_menu -> {
                val menuGithub = Intent(this, FavoriteActivity::class.java)
                startActivity(menuGithub)
                return true
            }
            else -> return true
        }
    }

    // ubah tema darkmode
    fun ChangeTheme() {
        themeViewModel.getThemeSetting().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

}