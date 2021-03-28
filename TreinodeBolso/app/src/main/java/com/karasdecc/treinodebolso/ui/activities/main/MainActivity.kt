package com.karasdecc.treinodebolso.ui.activities.main

import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.karasdecc.treinodebolso.R
import com.karasdecc.treinodebolso.databinding.ActivityMainBinding
import com.karasdecc.treinodebolso.ui.BaseBindingActivity
import com.karasdecc.treinodebolso.ui.fragments.main.HomeFragment
import com.karasdecc.treinodebolso.ui.fragments.main.ProfileFragment
import com.karasdecc.treinodebolso.ui.fragments.main.TrainingFragment

class MainActivity : BaseBindingActivity<ActivityMainBinding>(ActivityMainBinding::inflate),
    BottomNavigationView.OnNavigationItemSelectedListener{

    private var actualFragment = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        replaceFragment(HOME)
        setupBottomBar()
    }

    private fun setupBottomBar(){
        binding.mainBottomNavigation.setOnNavigationItemSelectedListener(this)
    }

    private fun replaceFragment(fragment : String){
        if(fragment == actualFragment)
            return
        val ft = supportFragmentManager.beginTransaction()
        when(fragment){
            HOME -> ft.replace(R.id.mainFragmentContainer,HomeFragment())
            TRAINING -> ft.replace(R.id.mainFragmentContainer,TrainingFragment())
            PROFILE -> ft.replace(R.id.mainFragmentContainer,ProfileFragment())
        }
        ft.commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.actionHome -> replaceFragment(HOME)
            R.id.actionTraining -> replaceFragment(TRAINING)
            R.id.actionProfile -> replaceFragment(PROFILE)
        }
        return true
    }

    companion object {
        const val HOME = "home"
        const val TRAINING = "training"
        const val PROFILE = "profile"
    }
}