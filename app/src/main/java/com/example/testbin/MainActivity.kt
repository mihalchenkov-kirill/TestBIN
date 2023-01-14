package com.example.testbin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.testbin.databinding.ActivityMainBinding
import com.example.testbin.fragments.BINSearchFragment
import com.example.testbin.fragments.ListFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        openFragment(BINSearchFragment.newInstance(), R.id.placeHolder)
    }

    override fun onResume() {
        super.onResume()
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.item_main -> {
                    openFragment(BINSearchFragment.newInstance(), R.id.placeHolder)
                }
                R.id.item_list -> {
                    openFragment(ListFragment.newInstance(), R.id.placeHolder)
                }
            }
            true
        }
    }

    private fun openFragment(fragment: Fragment, idHolder: Int) {
        supportFragmentManager
            .beginTransaction()
            .replace(idHolder, fragment)
            .commit()
    }
}