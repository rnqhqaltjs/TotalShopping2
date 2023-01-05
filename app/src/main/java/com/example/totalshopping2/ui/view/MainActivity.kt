package com.example.totalshopping2.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.totalshopping2.R
import com.example.totalshopping2.data.repository.ItemSearchRepositoryImpl
import com.example.totalshopping2.databinding.ActivityMainBinding
import com.example.totalshopping2.ui.viewmodel.ItemSearchViewModel
import com.example.totalshopping2.ui.viewmodel.ItemSearchViewModelProviderFactory

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    lateinit var itemSearchViewModel: ItemSearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupBottomNavigationView()
        if (savedInstanceState == null) {
            binding.bottomNavigationView.selectedItemId = R.id.fragment_search
        }

        val itemSearchRepository = ItemSearchRepositoryImpl()
        val factory = ItemSearchViewModelProviderFactory(itemSearchRepository)
        itemSearchViewModel = ViewModelProvider(this, factory)[ItemSearchViewModel::class.java]

    }

    private fun setupBottomNavigationView() {
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.fragment_search -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, SearchFragment())
                        .commit()
                    true
                }
                R.id.fragment_favorite -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, FavoriteFragment())
                        .commit()
                    true
                }
                R.id.fragment_settings -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, SettingsFragment())
                        .commit()
                    true
                }
                else -> false
            }
        }
    }
}