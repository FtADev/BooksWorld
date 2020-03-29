package com.ftadev.booksworld

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.appbar.*

class MainActivity : AppCompatActivity() {

    private val fragmentManager = supportFragmentManager
    private val firstFragment = AllBooksFragment()
    private val secondFragment = CategoryBooksFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.book_list_fragment, firstFragment)
        fragmentTransaction.commit()

        chips_group.setOnCheckedChangeListener { chipGroup, i ->
            val chip: Chip? = chipGroup.findViewById(i)
            if (chip != null) {
                if (chip.id == R.id.all_chip)
                    allBookClick()
                else
                    categoryBookClick()
            }
        }

    }


    private fun allBookClick() {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.book_list_fragment, firstFragment)
        fragmentTransaction.commit()
    }

    private fun categoryBookClick() {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.book_list_fragment, secondFragment)
        fragmentTransaction.commit()
    }

}
