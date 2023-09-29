package com.dicoding.submissiongithubapp.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submissiongithubapp.R
import com.dicoding.submissiongithubapp.databinding.FollowersBinding
import com.dicoding.submissiongithubapp.ui.activity.DetailUserActivity
import com.dicoding.submissiongithubapp.ui.adapter.ListUserAdapter
import com.dicoding.submissiongithubapp.ui.viewmodel.UserViewModel

class FollowersFragment : Fragment(R.layout.followers) {
    private var _binding: FollowersBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: UserViewModel
    private lateinit var adapter: ListUserAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        username = args?.getString(DetailUserActivity.EXTRA_USERNAME).toString()
        _binding = FollowersBinding.bind(view)

        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            rvUser.layoutManager = LinearLayoutManager(activity)
            rvUser.setHasFixedSize(true)
            rvUser.adapter = adapter
        }

        showLoading(true)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(UserViewModel::class.java)
        viewModel.setFollowers(username)
        viewModel.get().observe(viewLifecycleOwner, {
            if (it != null) {
                adapter.setList(it)
                showLoading(false)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}