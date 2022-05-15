package com.currencyconversion.app.ui.customViews

import android.widget.AutoCompleteTextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.databinding.BindingAdapter
import com.currencyconversion.app.ui.adapters.CustomDropDownAdapter
import java.lang.Exception

@BindingAdapter("setSelectionEnd")
fun editTextSetSelectionEndToText(editText:AppCompatEditText,text:String?){
    if (!text.isNullOrEmpty())
        try {
            editText.setSelection(text.length)
        }catch (e:Exception){}
}

@BindingAdapter("configureDropDown")
fun autoCompleteTextViewDropDown(autoCompleteTextView: AutoCompleteTextView,adapter:CustomDropDownAdapter){
    autoCompleteTextView.apply {
        threshold = 1
        setAdapter(adapter)
        setOnTouchListener { view, _ ->
            showDropDown()
            false
        }
    }
}