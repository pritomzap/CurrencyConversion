package com.currencyconversion.app.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.currencyconversion.app.R
import com.currencyconversion.app.databinding.ActivityMainBinding
import com.currencyconversion.app.service.network.NetworkResult
import com.currencyconversion.app.ui.customViews.CustomProgressDialog
import com.currencyconversion.app.ui.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var layoutBinding:ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        layoutBinding.root.post{
            attachObserver()

        }
        mainViewModel.getExchangeRates()
    }

    private fun attachObserver() {
        mainViewModel.responseExchangeRate.observe(this){response->
            executeNetworkResults(response)
        }
    }

    private fun <T> executeNetworkResults(response: NetworkResult<T>,showLoading:Boolean = true) {
        when (response) {
            is NetworkResult.Success -> {
                CustomProgressDialog.dismiss()
            }
            is NetworkResult.Error -> {
                CustomProgressDialog.dismiss()
            }
            is NetworkResult.Loading -> {
                try {
                    if (!CustomProgressDialog.isShowing()) CustomProgressDialog.show(this)
                }catch (e:Exception){ CustomProgressDialog.show(this) }
            }
        }
    }

}