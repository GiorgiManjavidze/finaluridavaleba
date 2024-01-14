package com.example.foodapp.presentation.screen.containers

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.foodapp.R
import com.example.foodapp.databinding.FragmentUserBinding
import com.example.foodapp.presentation.common.base.BaseFragment

class UserFragment : BaseFragment<FragmentUserBinding>(FragmentUserBinding::inflate) {

    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment

    private fun handleNavigationBar() = with(binding) {
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.homeIcon -> {
                    navController.navigate(R.id.homeNavigation)
                }
                R.id.favoritesIcon -> {
                    navController.navigate(R.id.favouritesNavigation)
                }
                R.id.profileIcon -> {
                    navController.navigate(R.id.profileFragment)
                }
            }
            true
        }
    }




    override fun init() {
        setUpNavGraph()
        handleNavigationBar()
    }

    private fun setUpNavGraph() {
        navHostFragment = childFragmentManager.findFragmentById(R.id.nav_host_fragment_main) as NavHostFragment
        navController = navHostFragment.findNavController()
        binding.bottomNavigationView.setupWithNavController(navController)
    }

}