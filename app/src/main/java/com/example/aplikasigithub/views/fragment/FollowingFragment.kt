package com.example.aplikasigithub.views.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplikasigithub.R
import com.example.aplikasigithub.adapter.FollowAdapter
import com.example.aplikasigithub.databinding.FragmentFollowingBinding
import com.example.aplikasigithub.viewmodels.HomeMaiinViewModel
import com.example.aplikasigithub.views.DetailActivity

class FollowingFragment : Fragment(R.layout.fragment_following) {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: FollowAdapter
    private lateinit var followingViewModel: HomeMaiinViewModel
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        username = args?.getString(DetailActivity.EXTRA_USERNAME).toString()
        _binding = FragmentFollowingBinding.bind(view)

        adapter = FollowAdapter()
        adapter.notifyDataSetChanged()
        binding.apply {
            rvFollowing.setHasFixedSize(true)
            rvFollowing.layoutManager = LinearLayoutManager(activity)
            rvFollowing.adapter = adapter
        }

        showLoading(true)
        followingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            HomeMaiinViewModel::class.java)
        followingViewModel.setUserFollowing(username)
        followingViewModel.getUserFollowing().observe(viewLifecycleOwner, {
            if(it!=null){
                adapter.setList(it)
                showLoading(false)
            }
        })
    }
    private fun showLoading(state: Boolean){
        if(state){
            binding.loadingFollowing.visibility = View.VISIBLE
        }else{
            binding.loadingFollowing.visibility = View.GONE
        }
    }
}