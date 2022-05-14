package com.currencyconversion.app.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.currencyconversion.app.R
import com.currencyconversion.app.databinding.FragmentSplashScreenBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenFragment : BaseFragment(R.layout.fragment_splash_screen) {
    private lateinit var layoutBinding:FragmentSplashScreenBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layoutBinding = getSpecificBinding()!!
        layoutBinding.root.post {
            lifecycleScope.launch {
                delay(3000)
                val action = SplashScreenFragmentDirections.actionSplashScreenFragmentToConverterFragment()
                findNavController().navigate(action)
            }
        }
    }

}