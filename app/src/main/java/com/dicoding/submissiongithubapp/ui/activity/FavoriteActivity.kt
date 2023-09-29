package com.dicoding.submissiongithubapp.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submissiongithubapp.data.local.entity.FavoriteEntity
import com.dicoding.submissiongithubapp.databinding.ActivityFavoriteBinding
import com.dicoding.submissiongithubapp.ui.adapter.FavoriteAdapter
import com.dicoding.submissiongithubapp.ui.viewmodel.Favorite
import com.dicoding.submissiongithubapp.ui.viewmodel.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {
    private var _binding: ActivityFavoriteBinding? = null
    private val binding get() = _binding

    private lateinit var adapter: FavoriteAdapter
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.title = "Favorite User"

        viewModel = obtainViewModel(this)

        adapter = FavoriteAdapter()
        binding?.rvUser?.layoutManager = LinearLayoutManager(this)
        binding?.rvUser?.setHasFixedSize(true)
        binding?.rvUser?.adapter = adapter

        viewModel.getAllFavorite().observe(this) {
            if (it != null) {
                val list = mappedList(it)
                adapter.setListFavorites(list)
            }
        }

        adapter.setOnClickCallback(object : FavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(favAdapter: FavoriteEntity) {
                intent = Intent(this@FavoriteActivity, DetailUserActivity::class.java)
                intent.putExtra(DetailUserActivity.EXTRA_USERNAME, favAdapter.login)
                intent.putExtra(DetailUserActivity.EXTRA_ID, favAdapter.id)
                startActivity(intent)
            }
        })
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = Favorite.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }

    private fun mappedList(list: List<FavoriteEntity>): ArrayList<FavoriteEntity> {
        val listFavorite = ArrayList<FavoriteEntity>()
        for (_favoriteUser in list) {
            val favoriteUsers = FavoriteEntity(
                _favoriteUser.id,
                _favoriteUser.login,
                _favoriteUser.avatarUrl
            )
            listFavorite.add(favoriteUsers)
        }
        return listFavorite
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}