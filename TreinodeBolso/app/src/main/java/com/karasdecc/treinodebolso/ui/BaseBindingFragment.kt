package com.karasdecc.treinodebolso.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.firebase.auth.FirebaseAuth
import com.karasdecc.treinodebolso.database.UserDatabaseManager
import com.karasdecc.treinodebolso.extensions.FragmentInflater
import org.koin.android.ext.android.inject

abstract class BaseBindingFragment<viewBinding : ViewBinding>(
    private val inflate: FragmentInflater<viewBinding>
) : Fragment() {

    protected val userDatabaseManager: UserDatabaseManager by inject()
    protected val auth: FirebaseAuth by inject()
    private var _binding: viewBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater,container,false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}