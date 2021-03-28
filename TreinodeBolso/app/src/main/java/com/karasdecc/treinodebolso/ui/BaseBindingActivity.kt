package com.karasdecc.treinodebolso.ui

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.karasdecc.treinodebolso.database.UserDatabaseManager
import com.karasdecc.treinodebolso.extensions.ActivityInflater
import org.koin.android.ext.android.inject

abstract class BaseBindingActivity<viewBinding : ViewBinding>(
    private val inflate: ActivityInflater<viewBinding>
): AppCompatActivity() {

    protected val userDatabaseManager: UserDatabaseManager by inject()

    private var _binding: viewBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}