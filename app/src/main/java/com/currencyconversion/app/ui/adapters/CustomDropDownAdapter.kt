package com.currencyconversion.app.ui.adapters

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Filter
import androidx.annotation.NonNull

class CustomDropDownAdapter : ArrayAdapter<Any?> {
    constructor(@NonNull context: Context?, resource: Int) : super(context!!, resource) {}
    constructor(@NonNull context: Context?, resource: Int, textViewResourceId: Int) : super(context!!, resource, textViewResourceId)
    constructor(@NonNull context: Context?, resource: Int, @NonNull objects: Array<Any?>?) : super(context!!, resource, objects!!)
    constructor(@NonNull context: Context?, resource: Int, textViewResourceId: Int, @NonNull objects: Array<Any?>?) : super(context!!, resource, textViewResourceId, objects!!)
    constructor(@NonNull context: Context?, resource: Int, @NonNull objects: List<*>?) : super(context!!, resource, objects!!)
    constructor(@NonNull context: Context?, resource: Int, textViewResourceId: Int, @NonNull objects: List<*>?) : super(context!!, resource, textViewResourceId, objects!!)

    @NonNull
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults? {
                return null
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {}
        }
    }
}