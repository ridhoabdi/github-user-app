package com.dicoding.submissiongithubapp.ui.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dicoding.submissiongithubapp.R
import com.dicoding.submissiongithubapp.data.local.entity.FavoriteEntity
import com.dicoding.submissiongithubapp.databinding.ActivityDetailUserBinding
import com.dicoding.submissiongithubapp.ui.adapter.SectionsPagerAdapter
import com.dicoding.submissiongithubapp.ui.viewmodel.Favorite
import com.dicoding.submissiongithubapp.ui.viewmodel.FavoriteViewModel
import com.dicoding.submissiongithubapp.ui.viewmodel.UserViewModel
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var favoriteEntity: FavoriteEntity

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers, R.string.following
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Detail User"

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)

        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)
        bundle.putString(EXTRA_ID, id.toString())

        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        favoriteViewModel = obtainViewModel(this)
        viewModel.setDetail(username.toString())
        viewModel.getDetail().observe(this) {
            if (it != null) {
                favoriteEntity = FavoriteEntity(
                    id,
                    it.login,
                    it.avatarUrl
                )
                showLoading(false)
                // menampilkan detail user
                binding.apply {
                    txtDetailName.text = it.name ?: "No Data Name"
                    txtDetailUsername.text = it.login
                    txtDetailFollowers.text = "${it.followers} Followers"
                    txtDetailFollowing.text = "${it.following} Following"
                    textDetailLocation.text = it.location ?: "No Data Location"
                    txtDetailCompany.text = it.company ?: "No Data Company"
                    txtDetailRepository.text = "${it.publicRepos} Repository"
                    Glide.with(this@DetailUserActivity)
                        .load(it.avatarUrl)
                        .into(txtDetailAvatar)
                }
            }
        }
        var Checked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = favoriteViewModel.check(id)
            withContext(Dispatchers.Main) {
                if (count != null) {
                    if (count > 0) {
                        Checked = true
                        binding.favoriteButton.isClickable = true
                        binding.favoriteButton.setImageResource(R.drawable.ic_baseline_favorite_24)
                    } else {
                        Checked = false
                        binding.favoriteButton.isClickable = true

                    }
                }
            }
        }

        binding.favoriteButton.setOnClickListener {
            Checked = !Checked
            if (Checked) {
                favoriteViewModel.insert(favoriteEntity)
                binding.favoriteButton.setImageResource(R.drawable.ic_baseline_favorite_24)
                Toast.makeText(
                    this,
                    "${favoriteEntity.login} telah ditambahkan ke Favorite User",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                favoriteViewModel.delete(id)
                binding.favoriteButton.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                Toast.makeText(
                    this,
                    "${favoriteEntity.login} telah dihapus dari Favorite User",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


        val sectionsPagerAdapter = SectionsPagerAdapter(this, bundle)
        binding.apply {
            viewPager.adapter = sectionsPagerAdapter
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
            supportActionBar?.elevation = 0f
        }
    }


    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = Favorite.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }

}