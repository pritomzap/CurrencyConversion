package com.currencyconversion.app.ui.fragments

import android.os.Bundle
import android.view.View
import com.currencyconversion.app.R
import com.currencyconversion.app.databinding.FragmentSplashScreenBinding

class SplashScreenFragment : BaseFragment(R.layout.fragment_splash_screen) {
    private lateinit var layoutBinding:FragmentSplashScreenBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layoutBinding = getSpecificBinding()!!
        layoutBinding.root.post {

        }
    }

}