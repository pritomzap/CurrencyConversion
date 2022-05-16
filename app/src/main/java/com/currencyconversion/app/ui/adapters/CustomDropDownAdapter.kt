package com.currencyconversion.app.ui.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import androidx.annotation.NonNull

class CustomDropDownAdapter : ArrayAdapter<String> {

    private var fullList:MutableList<String> = mutableListOf()

    constructor(@NonNull context: Context?, resource: Int, textViewResourceId: Int, @NonNull objects: List<String>) : super(context!!, resource, textViewResourceId, objects){
        this.fullList.addAll(objects)
    }

    override fun getCount(): Int {
        return fullList.size
    }

    override fun getItem(position: Int): String? {
        return fullList[position]
    }


    fun setData(data:List<String>){
        this.fullList.apply {
            clear()
            addAll(data)
        }
        this.notifyDataSetChanged()
    }

    @NonNull
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults? {
                return null
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {}
        }
    }

    fun getValueFromPosition(position: Int) = fullList[position]
}