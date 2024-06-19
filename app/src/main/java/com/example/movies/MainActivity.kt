package com.example.movies

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.example.movies.databinding.ActivityMainBinding
import com.example.movies.screens.main.MovieListFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Swati", "Activity onCreate")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        actionBarDrawerToggle = ActionBarDrawerToggle(this, binding.drawerLayout, binding.appBar, R.string.drawer_open, R.string.drawer_close)
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        setOptionMenu()

        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(R.id.frameLayout, MovieListFragment())
                .addToBackStack(null).commit()
        }
    }

    override fun onStop() {
        super.onStop()
        Log.d("Swati", "Activity onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Swati", "Activity onDestroy")
    }

    private fun setOptionMenu() {
        binding.drawerList.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.profile -> true
                R.id.favourite -> {
                    Toast.makeText(this, "Oops! Your favourite movie list is empty :(", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.logout -> {
                    Toast.makeText(this, "Logout function not working", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> {
                    Toast.makeText(this, "We provide wide range of movies", Toast.LENGTH_SHORT).show()
                    true
                }
            }
        }
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount

        if (count == 1) {
            super.onBackPressed()
            finish()
        } else {
            supportFragmentManager.popBackStack()
        }
    }
}