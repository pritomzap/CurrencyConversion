package com.currencyconversion.app.service.utils

import android.animation.LayoutTransition
import android.view.View
import android.view.ViewGroup


fun View.gone() {
    if (visibility != View.GONE)
        visibility = View.GONE
}

fun View.visible() {
    if (visibility != View.VISIBLE)
        visibility = View.VISIBLE
}

fun View.invisible() {
    if (visibility != View.INVISIBLE)
        visibility = View.INVISIBLE
}

fun View.enableDisableView(status:Boolean){
    if (status && (!isClickable || !isActivated)){
        isActivated = true
        isClickable = true
    }else if (!status && (isClickable || isActivated)){
        isActivated = false
        isClickable = false
    }
}

fun ViewGroup.animateLayoutChanges() {
    layoutTransition.enableTransitionType(LayoutTransition.CHANGING);
}


