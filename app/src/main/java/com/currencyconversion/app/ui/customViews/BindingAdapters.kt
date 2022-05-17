package com.currencyconversion.app.ui.customViews

import android.content.Context
import android.widget.AdapterView
import android.widget.AutoCompleteTextView
import android.widget.BaseAdapter
import androidx.appcompat.widget.AppCompatEditText
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.currencyconversion.app.ui.adapters.CommonRecyclerAdapter
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