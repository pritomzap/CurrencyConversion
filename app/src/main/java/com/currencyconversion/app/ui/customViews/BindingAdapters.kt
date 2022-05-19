package com.currencyconversion.app.ui.customViews

import android.graphics.Typeface
import android.icu.lang.UProperty.INT_START
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.widget.AdapterView
import android.widget.AutoCompleteTextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.currencyconversion.app.R
import com.currencyconversion.app.ui.adapters.CommonRecyclerAdapter
import com.currencyconversion.app.ui.adapters.CustomDropDownAdapter


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

@BindingAdapter("clickEvent")
fun autoCompleteTextViewItemClickListener(autoCompleteTextView: AutoCompleteTextView,listener: AdapterView.OnItemClickListener){
    autoCompleteTextView.apply {
        setOnItemClickListener(listener)
    }
}

@BindingAdapter("initRecyclerAdapter")
fun initRecyclerAdapter(recyclerView:RecyclerView,mAdapter:CommonRecyclerAdapter<Pair<String,String>>){
    recyclerView.apply {
        adapter = mAdapter
        layoutManager = GridLayoutManager(recyclerView.context,3)
        addItemDecoration(GridSpacingItemDecoration(3, 20, true))
    }
}

@BindingAdapter("setSpannableText")
fun getSpannableText(view:AppCompatTextView,text:String){
    val spannable = SpannableString(text)
    spannable.apply {
        setSpan(ForegroundColorSpan(ResourcesCompat.getColor(view.context.resources,R.color.colorGreenIdentical,null)), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        setSpan(RelativeSizeSpan(1.1f) , 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        setSpan(StyleSpan(Typeface.BOLD), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        setSpan(RelativeSizeSpan(1.1f) , 7, text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        setSpan(ForegroundColorSpan(ResourcesCompat.getColor(view.context.resources,R.color.colorPrimary,null)), 7, text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        setSpan(StyleSpan(Typeface.BOLD), 7, text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
    view.text = spannable
}