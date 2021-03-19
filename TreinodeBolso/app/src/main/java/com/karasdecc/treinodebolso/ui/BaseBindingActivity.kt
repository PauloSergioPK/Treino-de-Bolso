package com.karasdecc.treinodebolso.ui

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.karasdecc.treinodebolso.extensions.ActivityInflater

abstract class BaseBindingActivity<viewBinding : ViewBinding>(
    private val inflate: ActivityInflater<viewBinding>
): AppCompatActivity() {

    private var _binding: viewBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        _binding = inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}