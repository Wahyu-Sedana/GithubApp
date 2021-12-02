package com.example.aplikasigithub.views.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplikasigithub.R
import com.example.aplikasigithub.adapter.FollowAdapter
import com.example.aplikasigithub.databinding.FragmentFollowersBinding
import com.example.aplikasigithub.viewmodels.HomeMaiinViewModel
import com.example.aplikasigithub.views.DetailActivity

class FollowersFragment : Fragment(R.layout.fragment_followers) {

    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: FollowAdapter
    private lateinit var followersViewModel: HomeMaiinViewModel
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        username = args?.getString(DetailActivity.EXTRA_USERNAME).toString()
        _binding = FragmentFollowersBinding.bind(view)

        adapter = FollowAdapter()
        adapter.notifyDataSetChanged()
        binding.apply {
            rvFollower.setHasFixedSize(true)
            rvFollower.layoutManager = LinearLayoutManager(activity)
            rvFollower.adapter = adapter
        }

        showLoading(true)
        followersViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(HomeMaiinViewModel::class.java)
        followersViewModel.setUserFollower(username)
        followersViewModel.getUserFollower().observe(viewLifecycleOwner, {
            if(it!=null){
                adapter.setList(it)
                showLoading(false)
            }
        })
    }
    private fun showLoading(state: Boolean){
        if(state){
            binding.loadingFollowers.visibility = View.VISIBLE
        }else{
            binding.loadingFollowers.visibility = View.GONE
        }
    }
}