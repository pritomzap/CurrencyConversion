package com.currencyconversion.app.ui.customViews

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.currencyconversion.app.R
import com.currencyconversion.app.databinding.LayoutCustomToastBinding
import com.currencyconversion.app.service.utils.gone
import javax.inject.Inject

class CustomToast @Inject constructor(private val context: Context): Toast(context) {

    private var layoutBinding: LayoutCustomToastBinding =
        DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.layout_custom_toast,
            null,
            false
        )

    fun showSuccessToast(message:String){
        layoutBinding.apply {
            toastText.text = message
            toastText.setTextColor(ContextCompat.getColor(context, R.color.colorGreenIdentical))
            negativeIcon.gone()
        }
        view = layoutBinding.root
        otherParams()
    }
    fun showFailedToast(message:String){
        layoutBinding.apply {
            toastText.text = message
            toastText.setTextColor(
                ContextCompat.getColor(
                    context,R.color.colorRedBackground
                )
            )
            postiveIcon.gone()
        }
        view = layoutBinding.root
        otherParams()
    }

    private fun otherParams(){
        setGravity(Gravity.BOTTOM, 0, 0)
        duration = LENGTH_SHORT
        show()
    }
}