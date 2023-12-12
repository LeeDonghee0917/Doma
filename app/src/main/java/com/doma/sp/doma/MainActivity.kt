package com.doma.sp.doma

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import com.doma.sp.doma.book.BookFragment
import com.doma.sp.doma.databinding.ActivityMainBinding
import com.doma.sp.doma.map.MapFragment
import com.doma.sp.doma.search.SearchFragment
import java.security.MessageDigest

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initBottomNavigation()
    }

    private fun initBottomNavigation() {

        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_changeFragment, BookFragment())
            .commitAllowingStateLoss()

        binding.bnvBottomBar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.frag_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fl_changeFragment, BookFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.frag_search -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fl_changeFragment, SearchFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.frag_settings -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fl_changeFragment, MapFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                else -> false
            }
        }
    }
}