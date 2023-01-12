package com.example.totalshopping2.ui.view

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.totalshopping2.R
import com.example.totalshopping2.data.db.ItemSearchDatabase
import com.example.totalshopping2.data.repository.ItemSearchRepositoryImpl
import com.example.totalshopping2.databinding.ActivityMainBinding
import com.example.totalshopping2.ui.viewmodel.ItemSearchViewModel
import com.example.totalshopping2.ui.viewmodel.ItemSearchViewModelProviderFactory
import com.example.totalshopping2.util.Constants.DATASTORE_NAME

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    lateinit var itemSearchViewModel: ItemSearchViewModel
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    private val Context.dataStore by preferencesDataStore(DATASTORE_NAME)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        setupBottomNavigationView()
//        if (savedInstanceState == null) {
//            binding.bottomNavigationView.selectedItemId = R.id.fragment_search
//        }
        setupJetpackNavigation()

        val database = ItemSearchDatabase.getInstance(this)
        val itemSearchRepository = ItemSearchRepositoryImpl(database, dataStore)
        val factory = ItemSearchViewModelProviderFactory(itemSearchRepository, this)
        itemSearchViewModel = ViewModelProvider(this, factory)[ItemSearchViewModel::class.java]

    }

    private fun setupJetpackNavigation() {
        val host = supportFragmentManager
            .findFragmentById(R.id.itemsearch_nav_host_fragment) as NavHostFragment? ?: return
        navController = host.navController
        binding.bottomNavigationView.setupWithNavController(navController)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.fragment_search,
                R.id.fragment_favorite,
                R.id.fragment_settings
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

//    private fun setupBottomNavigationView() {
//        binding.bottomNavigationView.setOnItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.fragment_search -> {
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.frame_layout, SearchFragment())
//                        .commit()
//                    true
//                }
//                R.id.fragment_favorite -> {
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.frame_layout, FavoriteFragment())
//                        .commit()
//                    true
//                }
//                R.id.fragment_settings -> {
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.frame_layout, SettingsFragment())
//                        .commit()
//                    true
//                }
//                else -> false
//            }
//        }
//    }
}