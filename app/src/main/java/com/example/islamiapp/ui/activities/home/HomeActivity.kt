package com.example.islamiapp.ui.activities.home

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.islamiapp.R
import com.example.islamiapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var mBinding : ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityHomeBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.findNavController()
        mBinding.islamiBottomNav.setupWithNavController(navController)

    }
}