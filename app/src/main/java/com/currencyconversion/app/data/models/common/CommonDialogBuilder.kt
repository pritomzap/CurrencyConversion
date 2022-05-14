package com.currencyconversion.app.data.models.common

import android.graphics.drawable.Drawable

data class CommonDialogBuilder(
    var type:CommonDialogType = CommonDialogType.ERROR,
    var titleText:String? = null,
    var subTitle:String? = null,
    var buttonText:String? = null,
    var listener:(()->Unit)? = null,
    var titleImage:Drawable? = null,
    var closeIconVisibility:Boolean? = true,
    var closeIconClickListener:(()->Unit)? = null){
    enum class CommonDialogType{
        ERROR,
        SUCCESS
    }
}
