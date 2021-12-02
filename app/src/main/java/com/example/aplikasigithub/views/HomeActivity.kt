package com.example.aplikasigithub.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplikasigithub.R
import com.example.aplikasigithub.adapter.SearchAdapter
import com.example.aplikasigithub.databinding.ActivityHomeBinding
import com.example.aplikasigithub.models.searchdata.ResponseSearchData
import com.example.aplikasigithub.viewmodels.HomeMaiinViewModel
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.user_item.*

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var homeModel: HomeMaiinViewModel
    private lateinit var adapter: SearchAdapter
    private lateinit var layoutEmpty: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.title = "Users"

        checkTheme()

        layoutEmpty = findViewById(R.id.layoutEmpty)
        adapter = SearchAdapter()
        adapter.notifyDataSetChanged()

        adapter.setOnItemCallBack(object : SearchAdapter.OnItemClickCallBack {
            override fun onItemClicked(data: ResponseSearchData) {
                startActivity(Intent(this@HomeActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailActivity.EXTRA_AVATAR, data.avatarUrl)
                    it.putExtra(DetailActivity.EXTRA_URL, data.url)
                })
            }

        })
        homeModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(HomeMaiinViewModel::class.java)

        binding.apply {
            rvListUser.layoutManager = LinearLayoutManager(this@HomeActivity)
            rvListUser.setHasFixedSize(true)
            rvListUser.adapter = adapter

            btnSearch.setOnClickListener {
                searchUser()
            }
            btnClear.setOnClickListener {
                searchData.text.clear()
                btnClear.visibility = View.GONE
                layoutEmpty.visibility = View.VISIBLE
                rvListUser.visibility = View.GONE
            }

            searchData.setOnKeyListener { _, i, keyEvent ->
                if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {
                    searchUser()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }

            homeModel.getSearchUser().observe(this@HomeActivity, {
                if (it != null) {
                    adapter.setList(it)
                    showLoading(false)
                    layoutEmpty.visibility = View.GONE
                    rvListUser.visibility = View.VISIBLE
                    if (it.size == 0) {
                        showLoading(false)
                        layoutEmpty.visibility = View.VISIBLE
                        Toast.makeText(
                            this@HomeActivity,
                            "data tidak di temukan",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            })
        }
    }

    private fun searchUser() {
        binding.apply {
            val query = searchData.text.toString()
            if (query.isEmpty()) {
                Toast.makeText(this@HomeActivity, "Form tidak boleh kosong", Toast.LENGTH_LONG)
                    .show()
            } else {
                showLoading(true)
                homeModel.setSearchUser(query)
                btnClear.visibility = View.VISIBLE
                layoutEmpty.visibility = View.GONE

            }
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun chooseThemeDialog() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Choose Theme")
        val styles = arrayOf("Light", "Dark", "System default")
        val checkedItem = MyPreferences(this).darkMode

        builder.setSingleChoiceItems(styles, checkedItem) { dialog, which ->

            when (which) {
                0 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    MyPreferences(this).darkMode = 0
                    delegate.applyDayNight()
                    dialog.dismiss()
                }
                1 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    MyPreferences(this).darkMode = 1
                    delegate.applyDayNight()
                    dialog.dismiss()
                }
                2 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    MyPreferences(this).darkMode = 2
                    delegate.applyDayNight()
                    dialog.dismiss()
                }

            }
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun checkTheme() {
        when (MyPreferences(this).darkMode) {
            0 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                delegate.applyDayNight()
            }
            1 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                delegate.applyDayNight()
            }
            2 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                delegate.applyDayNight()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_action, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite -> {
                Intent(this, FavoriteActivity::class.java).also {
                    startActivity(it)
                }
            }

            R.id.setting -> {
                chooseThemeDialog()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}