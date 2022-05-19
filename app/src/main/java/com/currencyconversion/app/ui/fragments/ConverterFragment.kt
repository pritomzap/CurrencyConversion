package com.currencyconversion.app.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.currencyconversion.app.R
import com.currencyconversion.app.data.models.common.CommonDialogBuilder
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
            viewModel.getAllCurrencies()
            viewModel.initConvertedAmountRecycler()
        }
    }

    override fun attachResumableObserver(){
        viewModel.currencies.observe(viewLifecycleOwner){response->
            (activity as MainActivity).executeNetworkResults(response, doOnSuccess = {
                when(response.data?.success){
                    true-> viewModel.setCurrencyToAdapter(response.data.currencies?.keys?.toList()?: emptyList())
                    false-> (requireActivity() as MainActivity).showCommonDialog(CommonDialogBuilder(type = CommonDialogBuilder.CommonDialogType.ERROR,subTitle = response.message))
                    else -> (requireActivity() as MainActivity).showCommonDialog(CommonDialogBuilder(type = CommonDialogBuilder.CommonDialogType.ERROR,subTitle = resources.getString(R.string.app_common_api_error)))
                }
            }, doOnError = {
                (requireActivity() as MainActivity).showCommonDialog(CommonDialogBuilder(type = CommonDialogBuilder.CommonDialogType.ERROR,subTitle = response.message?:resources.getString(R.string.app_common_api_error)))
            })
        }
        viewModel.responseExchangeRate.observe(viewLifecycleOwner){response->
            (activity as MainActivity).executeNetworkResults(response,showLoading = false, doOnSuccess = {
                when(response.data?.success){
                    true-> viewModel.convertedCurrenciesAdapter.listOfItems = response.data.quotes?.entries?.map { Pair(it.key, it.value.toString()) }?.toMutableList()
                    false-> (requireActivity() as MainActivity).showCommonDialog(CommonDialogBuilder(type = CommonDialogBuilder.CommonDialogType.ERROR,subTitle = response.message))
                    else -> (requireActivity() as MainActivity).showCommonDialog(CommonDialogBuilder(type = CommonDialogBuilder.CommonDialogType.ERROR,subTitle = resources.getString(R.string.app_common_api_error)))
                }
            }, doOnError = {
                (requireActivity() as MainActivity).showCommonDialog(CommonDialogBuilder(type = CommonDialogBuilder.CommonDialogType.ERROR,subTitle = response.message?:resources.getString(R.string.app_common_api_error)))
            })
        }
    }
}