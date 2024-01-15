package com.example.foodapp.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.foodapp.R
import com.example.foodapp.databinding.ActivityMainBinding
import com.example.foodapp.presentation.screen.containers.AuthFragment
import com.example.foodapp.presentation.screen.containers.UserFragment
import com.example.foodapp.receiver.NetworkChangeReceiver
import com.example.foodapp.worker.RecipeWorker
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var receiver: NetworkChangeReceiver

    @SuppressLint("InlinedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkAuthenticationStatus()
        startRecipeWorker()

        receiver = NetworkChangeReceiver()
        IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED).also {
            registerReceiver(receiver, it, RECEIVER_NOT_EXPORTED)
        }
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

    override fun onStop() {
        super.onStop()
        unregisterReceiver(receiver)
    }


    private fun startRecipeWorker() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val periodicWorkRequest = PeriodicWorkRequestBuilder<RecipeWorker>(
            repeatInterval = 10,
            repeatIntervalTimeUnit = TimeUnit.MINUTES
        )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "RecipeWorker",
            ExistingPeriodicWorkPolicy.KEEP,
            periodicWorkRequest
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}