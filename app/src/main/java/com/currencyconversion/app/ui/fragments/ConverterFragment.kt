package com.currencyconversion.app.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.currencyconversion.app.R
import com.currencyconversion.app.databinding.FragmentConverterBinding


class ConverterFragment : BaseFragment(R.layout.fragment_converter) {
    private lateinit var layoutBinding:FragmentConverterBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layoutBinding = getSpecificBinding()!!
    }
}