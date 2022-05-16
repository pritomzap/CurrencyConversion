package com.currencyconversion.app.ui.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment


abstract class BaseFragment(@LayoutRes val layout: Int) : Fragment() {

    open var binding: ViewDataBinding? = null
    var mActivity: AppCompatActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is AppCompatActivity) {
            val activity = context
            mActivity = activity
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        attachSingleObserver()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, layout, container, false)
        return binding?.root ?: inflater.inflate(layout, container, false)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        attachResumableObserver()
        // Set up touch listener for non-text box views to hide keyboard.
        if (binding?.root !is EditText) {
            binding?.root?.setOnTouchListener { v, event ->
                hideSoftKeyboard()
                false
            }
        }
    }

    open fun attachSingleObserver(){}

    open fun attachResumableObserver(){}

    inline fun <reified specific : ViewDataBinding> getSpecificBinding() = binding as? specific

    private fun hideSoftKeyboard() {
        val inputMethodManager = mActivity?.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        if (inputMethodManager.isAcceptingText) {
            inputMethodManager.hideSoftInputFromWindow(mActivity?.currentFocus!!.windowToken, 0)
        }
    }
}