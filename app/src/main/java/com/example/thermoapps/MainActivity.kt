package com.example.thermoapps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import com.example.thermoapps.about.AboutFragment
import com.example.thermoapps.home.HomePageFragment
import com.example.thermoapps.prediction.PredictionFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var toolbar: ActionBar

    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = supportActionBar!!
        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        bottomNavigation.selectedItemId = R.id.menu_home
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            this.finish();
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Tekan Back Sekali Lagi Untuk Keluar", Toast.LENGTH_SHORT).show()

        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.menu_home -> {
                toolbar.title = "Beranda"
                val homePageFragment = HomePageFragment.newInstance()
                openFragment(homePageFragment)
                return@OnNavigationItemSelectedListener true
            }

            R.id.menu_predict -> {
                toolbar.title = "Prediksi"
                val predictionFragment = PredictionFragment.newInstance()
                openFragment(predictionFragment)
                return@OnNavigationItemSelectedListener true
            }

            R.id.menu_about -> {
                toolbar.title = "Info"
                val aboutFragment = AboutFragment.newInstance()
                openFragment(aboutFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun bottomNavigationButtonState(isEnabled: Boolean) {
        bottom_navigation.menu.forEach {it.isEnabled = isEnabled }
    }
}