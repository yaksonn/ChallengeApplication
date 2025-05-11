package com.yakson.sportbetapp.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.yakson.sportbetapp.R
import com.yakson.sportbetapp.base.BaseActivity
import com.yakson.sportbetapp.databinding.ActivityMainBinding
import com.yakson.sportbetapp.ui.auth.AuthViewModel
import com.yakson.sportbetapp.ui.basket.BetBasketViewModel
import com.yakson.sportbetapp.ui.bulletin.BetBulletinFragmentDirections
import com.yakson.sportbetapp.ui.basket.BetBasketFragmentDirections
import com.yakson.sportbetapp.ui.detail.BetDetailFragmentDirections
import com.yakson.sportbetapp.util.showView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, AuthViewModel>() {

    override val viewModel: AuthViewModel by viewModels()
    private val basketViewModel: BetBasketViewModel by viewModels()
    private lateinit var navController: NavHostFragment

    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        navController = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        setSupportActionBar(binding.toolbar.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        var keepSplashScreen = true
        splashScreen.setKeepOnScreenCondition { keepSplashScreen }

        viewModel.currentUser.observe(this) { user ->
            val destination = if (user != null) {
                R.id.betBulletinFragment
            } else {
                R.id.welcomeFragment
            }

            navController.navController.navigate(destination)
            keepSplashScreen = false
        }

        lifecycleScope.launch {
            basketViewModel.basket.collect { basket ->
                binding.toolbar.basketBadge.apply {
                    showView(basket.isNotEmpty())
                    text = basket.size.toString()
                }
            }
        }

        setupToolbarClickListeners()
        setupNavigationListener()
    }

    private fun setupToolbarClickListeners() = with(binding) {
        toolbar.settingsIcon.setOnClickListener {
            navigateToSettings()
        }

        toolbar.basketIcon.setOnClickListener {
            navigateToBasket()
        }
        toolbar.backIcon.setOnClickListener {
            navController.navController.navigateUp()
        }
    }

    private fun setupNavigationListener() {
        navController.navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.settingsFragment, R.id.betBasketFragment, R.id.betDetailFragment -> {
                    binding.toolbar.backIcon.showView(show = true)
                    binding.toolbar.toolbarTitle.text = when (destination.id) {
                        R.id.settingsFragment -> getString(R.string.settings)
                        R.id.betBasketFragment -> getString(R.string.basket)
                        R.id.betDetailFragment -> getString(R.string.match_detail)
                        else -> getString(R.string.sport_bet)
                    }
                }

                R.id.welcomeFragment, R.id.loginFragment, R.id.registerFragment -> {
                    binding.toolbar.root.showView(show = false)
                }

                else -> {
                    binding.toolbar.root.showView(show = true)
                    binding.toolbar.backIcon.showView(show = false)
                    binding.toolbar.toolbarTitle.text = getString(R.string.sport_bet)
                }
            }
        }
    }

    private fun navigateToSettings() {
        val currentDestination = navController.navController.currentDestination?.id
        when (currentDestination) {
            R.id.betBulletinFragment -> {
                navController.navController.navigate(
                    BetBulletinFragmentDirections.actionBetBulletinFragmentToSettingsFragment()
                )
            }

            R.id.betBasketFragment -> {
                navController.navController.navigate(
                    BetBasketFragmentDirections.actionBetBasketFragmentToSettingsFragment()
                )
            }
        }
    }

    private fun navigateToBasket() {
        val currentDestination = navController.navController.currentDestination?.id
        when (currentDestination) {
            R.id.betBulletinFragment -> {
                navController.navController.navigate(
                    BetBulletinFragmentDirections.actionBetBulletinFragmentToBetBasketFragment()
                )
            }

            R.id.betDetailFragment -> {
                navController.navController.navigate(
                    BetDetailFragmentDirections.actionBetDetailFragmentToBetBasketFragment()
                )
            }
        }
    }
}