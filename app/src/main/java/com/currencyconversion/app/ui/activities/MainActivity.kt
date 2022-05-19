package com.currencyconversion.app.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.currencyconversion.app.R
import com.currencyconversion.app.data.models.common.CommonDialogBuilder
import com.currencyconversion.app.databinding.ActivityMainBinding
import com.currencyconversion.app.service.network.NetworkResult
import com.currencyconversion.app.service.utils.ConnectionStateMonitor
import com.currencyconversion.app.ui.customViews.CommonDialogFragment
import com.currencyconversion.app.ui.customViews.CustomProgressDialog
import com.currencyconversion.app.ui.viewModels.DialogFragmentViewModel
import com.currencyconversion.app.ui.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var networkStateMonitor:ConnectionStateMonitor
    private lateinit var layoutBinding:ActivityMainBinding
    private val dialogFragmentViewModel by viewModels<DialogFragmentViewModel>()
    private val mainViewModel by viewModels<MainViewModel>()
    private val commonDialogFragment by lazy { CommonDialogFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)
    }

    fun <T> executeNetworkResults(response: NetworkResult<T>, showLoading:Boolean = true, doOnLoading: (() -> Unit)? = null, doOnSuccess: (() -> Unit)? = null, doOnError: (() -> Unit)? = null) {
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

    fun showCommonDialog(dialogBuilder: CommonDialogBuilder){
        if (commonDialogFragment.isAdded) commonDialogFragment.dismiss()
        dialogFragmentViewModel.setData(dialogBuilder)
        commonDialogFragment.show(supportFragmentManager,"dialog")
    }

    override fun onResume() {
        super.onResume()
        networkStateMonitor.observe(this) { isConnected ->
            isConnected?.let {
                if (it) {
                    mainViewModel.isNetworkAvailable.value = true
                    if (commonDialogFragment.isAdded) commonDialogFragment.dismiss()
                } else {
                    mainViewModel.isNetworkAvailable.value = false
                    showCommonDialog(
                        CommonDialogBuilder(
                            type = CommonDialogBuilder.CommonDialogType.ERROR,
                            titleText = getString(R.string.network_error_title),
                            subTitle = getString(R.string.network_error_subtitle),
                            buttonText = getString(R.string.go_to_setting),
                            closeIconVisibility = false,
                            listener = { startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))},
                        )
                    )
                }
            }
        }
    }

}