package com.example.alyshevyaminovapp.presentaion.home

import android.os.Bundle
import com.example.alyshevyaminovapp.R
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import com.example.alyshevyaminovapp.presentaion.home.fragments.HomeFragment
import com.example.alyshevyaminovapp.presentaion.home.fragments.ProfileFragment
import com.example.alyshevyaminovapp.presentaion.home.fragments.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeNavigation  : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.home_navigation_screen)
        val bottomNavigationView : BottomNavigationView = findViewById(R.id.bottomNavigationView)
        val homeFragment = HomeFragment()
        val profileFragment = ProfileFragment()
        val settingsFragment = SettingsFragment()
        setCurrentFragment(homeFragment)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId){
                R.id.home -> setCurrentFragment(homeFragment)
                R.id.profile -> setCurrentFragment(profileFragment)
                R.id.settings -> setCurrentFragment(settingsFragment)
            }
            true
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) {v,insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top,systemBars.right,systemBars.bottom)
            insets
        }
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }
}