package com.currencyconversion.app.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.currencyconversion.app.R
import com.currencyconversion.app.databinding.FragmentConverterBinding
import com.currencyconversion.app.service.network.NetworkResult
import com.currencyconversion.app.ui.activities.MainActivity
import com.currencyconversion.app.ui.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConverterFragment : BaseFragment(R.layout.fragment_converter) {
    private lateinit var layoutBinding:FragmentConverterBinding
    private val viewModel by viewModels<MainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layoutBinding = getSpecificBinding()!!
        layoutBinding.apply {
            mainViewModel = viewModel
            lifecycleOwner = this@ConverterFragment
            viewModel.getExchangeRates()
        }
    }

    override fun attachResumableObserver(){
        viewModel.responseExchangeRate.observe(viewLifecycleOwner){response->
            (activity as MainActivity).executeNetworkResults(response, doOnSuccess = {
                viewModel.setCurrencyToAdapter(response.data?.rates?.keys?.toList()?: emptyList())
            })
        }
    }
}