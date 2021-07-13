package com.example.dagger2sample.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dagger2sample.R
import com.example.dagger2sample.databinding.ActivityMainBinding
import com.example.dagger2sample.ui.user.UserFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .disallowAddToBackStack()
            .replace(R.id.frameLayout, UserFragment().newInstance(), "UserFragment").commit()
    }

    override fun onBackPressed() {
        val fragmentManager = supportFragmentManager
        val fragment = fragmentManager.findFragmentByTag("UserFragment")

        if (fragment == null) {
            supportFragmentManager.popBackStackImmediate()
        } else {
            super.onBackPressed()
        }
    }
}