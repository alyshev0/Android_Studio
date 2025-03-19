package com.example.alyshevyaminovapp.presentaion.home

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.example.alyshevyaminovapp.R
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.FragmentTransitionImpl
import com.example.alyshevyaminovapp.data.DbHelper
import com.example.alyshevyaminovapp.databinding.HomeNavigationScreenBinding
import com.example.alyshevyaminovapp.presentaion.Authorization
import com.example.alyshevyaminovapp.presentaion.home.fragments.CartFragment
import com.example.alyshevyaminovapp.presentaion.home.fragments.HomeFragment
import com.example.alyshevyaminovapp.presentaion.home.fragments.ItemFragment
import com.example.alyshevyaminovapp.presentaion.home.fragments.ProfileFragment
import com.example.alyshevyaminovapp.presentaion.home.fragments.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.navigation.NavigationView

class HomeNavigation : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var fragmentManager: FragmentManager
    private lateinit var binding: HomeNavigationScreenBinding
    private  lateinit var  userEmail:String
    private lateinit var userLogin:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = HomeNavigationScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.nav_open,
            R.string.nav_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navigationView.setNavigationItemSelectedListener(this)
        val sharedPreferences = getSharedPreferences("users_prefs", MODE_PRIVATE)
        userLogin = sharedPreferences.getString("login","") ?: ""
        userEmail = sharedPreferences.getString("email","") ?: ""
        updateNavigationHeader(userLogin,userEmail)


        binding.bottomNavigationView.background = null
        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.botton_home -> openFragment(HomeFragment())
                R.id.bottom_profile -> openFragment(ProfileFragment())
                R.id.bottom_cart -> openFragment(CartFragment())
                R.id.bottom_settings -> openFragment(SettingsFragment())
            }
            true
        }

        fragmentManager = supportFragmentManager
        openFragment(HomeFragment())

        binding.fab.setOnClickListener {
            Toast.makeText(this, "+", Toast.LENGTH_SHORT).show()
        }

        binding.fab.setOnClickListener(){
            openFragment(ItemFragment())
        }
    }


    fun updateNavigationHeader(login:String,email:String){
        val headerView = binding.navigationView.getHeaderView(0)
        val headerLogin = headerView.findViewById<TextView>(R.id.drawer_header_login)
        val headerEmail = headerView.findViewById<TextView>(R.id.drawer_header_email)
        val headerImage = headerView.findViewById<ShapeableImageView>(R.id.drawer_header_image)

        headerLogin.text = login
        headerEmail.text = email
        val DbHelper = DbHelper(this,null)
        val bitmap = DbHelper.loadProfileImage(email)
        if (bitmap != null){
            headerImage.setImageBitmap(bitmap)
        }else{
            headerImage.setImageResource(R.drawable.icons_default_avatar)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> openFragment(HomeFragment())
            R.id.profile -> openFragment(ProfileFragment())
            R.id.cart -> openFragment(CartFragment())
            R.id.settings -> openFragment(SettingsFragment())
            R.id.logout -> Toast.makeText(this, "Logout clicked", Toast.LENGTH_SHORT).show()
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private  fun openProfileFragment(){
        val sharedPreferences = getSharedPreferences("users_prefs", MODE_PRIVATE)
        val login  = sharedPreferences.getString("login","") ?: ""
        val email = sharedPreferences.getString("email","") ?: ""
        val profileFragment = ProfileFragment().apply {
            arguments = Bundle().apply {
                putString("login",login)
                putString("email",email)
            }
        }
        openFragment(profileFragment)
    }
    private fun logoutUser() {
        // Второй диалог для выхода из приложения
        MaterialAlertDialogBuilder(this).apply {
            setTitle("Подтверждение")
            setMessage("Вы уверены, что хотите выйти из аккаунта?")

            setPositiveButton("Да") { dialog, which ->
                Toast.makeText(this@HomeNavigation, "Выходим из аккаунта...", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@HomeNavigation, Authorization::class.java))
                finish()
            }

            setNegativeButton("Отмена") { dialog, which ->
                dialog.cancel()
            }
        }.show()
    }
    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun openFragment(fragment: Fragment) {
        val fragmentTransition: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.flFragment, fragment)
        fragmentTransition.commit()
    }


}