package com.ftadev.booksworld.ui.activity

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.ftadev.booksworld.R

class BookActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // For gradient status bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        }

        setContentView(R.layout.activity_book)

    }


}