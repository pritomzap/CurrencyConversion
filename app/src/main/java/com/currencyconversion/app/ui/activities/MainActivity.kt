package com.currencyconversion.app.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.currencyconversion.app.R
import com.currencyconversion.app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var layoutBinding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)
    }

}