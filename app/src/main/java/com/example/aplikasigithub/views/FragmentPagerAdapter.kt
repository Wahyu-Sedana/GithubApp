package com.example.aplikasigithub.views
import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.aplikasigithub.R
import com.example.aplikasigithub.views.fragment.FollowersFragment
import com.example.aplikasigithub.views.fragment.FollowingFragment

class FragmentPagerAdapter(private val context: Context,fm: FragmentManager, data: Bundle): FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
    private var fragmentBundle: Bundle
    @StringRes
    private val TAB_TITLE = intArrayOf(R.string.tab1, R.string.tab2)
    init {
        fragmentBundle = data
    }

        override fun getItem(position: Int):Fragment {
            var fragment: Fragment? = null
            when (position) {
                0 -> {
                    fragment = FollowersFragment()
                }
                1 -> {
                    fragment = FollowingFragment()
                }
                else -> getItem(position)
            }
            fragment?.arguments = this.fragmentBundle
            return fragment as Fragment
        }
        override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence?{
        return context.resources.getString(TAB_TITLE[position])
    }
}