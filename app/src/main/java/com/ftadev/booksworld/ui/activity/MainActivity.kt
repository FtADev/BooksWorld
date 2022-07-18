package com.ftadev.booksworld.ui.activity

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
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
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
    }

}