package com.example.foodapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.foodapp.R
import com.example.foodapp.databinding.ActivityMainBinding
import com.example.foodapp.presentation.screen.containers.AuthFragment
import com.example.foodapp.presentation.screen.containers.UserFragment
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkAuthenticationStatus()
    }

    private fun checkAuthenticationStatus() {
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        if (currentUser == null) {
            showAuthFragment()
        } else {
            showUserFragment()
        }
    }

    private fun showAuthFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, AuthFragment())
            .commit()
    }

    private fun showUserFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, UserFragment())
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}