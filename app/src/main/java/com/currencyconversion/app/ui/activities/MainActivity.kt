package com.currencyconversion.app.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.currencyconversion.app.R
import com.currencyconversion.app.databinding.ActivityMainBinding
import com.currencyconversion.app.service.network.NetworkResult
import com.currencyconversion.app.ui.customViews.CustomProgressDialog
import com.currencyconversion.app.ui.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var layoutBinding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)
    }

    fun <T> executeNetworkResults(
        response: NetworkResult<T>,
        showLoading:Boolean = true,
        doOnLoading: (() -> Unit)? = null,
        doOnSuccess: (() -> Unit)? = null,
        doOnError: (() -> Unit)? = null
    ) {
        when (response) {
            is NetworkResult.Success -> {
                CustomProgressDialog.dismiss()
                doOnSuccess?.invoke()
            }
            is NetworkResult.Error -> {
                CustomProgressDialog.dismiss()
                doOnError?.invoke()
            }
            is NetworkResult.Loading -> {
                try {
                    if (!CustomProgressDialog.isShowing()) CustomProgressDialog.show(this)
                }catch (e:Exception){ CustomProgressDialog.show(this) }
                doOnLoading?.invoke()
            }
        }
    }

}