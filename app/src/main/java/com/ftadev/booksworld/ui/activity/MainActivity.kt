package com.ftadev.booksworld.ui.activity

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.ftadev.booksworld.R
import androidx.navigation.Navigation.findNavController

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        makeStatusBarGradient()

        setContentView(R.layout.activity_main)
    }


    override fun onSupportNavigateUp() =
        findNavController(this, R.id.navHostFragment).navigateUp()


    private fun makeStatusBarGradient() {
        val window = window
        val background = ContextCompat.getDrawable(this, R.drawable.bg_appbar)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this,android.R.color.transparent)
        window.navigationBarColor = ContextCompat.getColor(this,android.R.color.transparent)
        window.setBackgroundDrawable(background)
    }

}