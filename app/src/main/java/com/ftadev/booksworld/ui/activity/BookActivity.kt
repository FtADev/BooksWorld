package com.ftadev.booksworld.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View.GONE
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ftadev.booksworld.R
import com.ftadev.booksworld.ui.RoundedTransformation
import com.ftadev.booksworld.ui.viewmodel.MainViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_book.*
import kotlinx.android.synthetic.main.card_book.view.*


class BookActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        }

        setContentView(R.layout.activity_book)

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        registerObservers()

        val id = intent.getIntExtra("ID", 0)
        mainViewModel.getBookInfo(id)
    }

    private fun registerObservers() {
        mainViewModel.bookInfoSuccessLiveData.observe(this, Observer { book ->
            book?.let {
                book_title.text = book.name
                author.text = book.author
                if (book.category != null)
                    category.text = book.category
                else
                    category.visibility = GONE
                Picasso.get().load(book.photo).transform(RoundedTransformation(20,0)).into(photo)
                rb.rating = book.rate.toFloat()
                page_num.text = book.pageNumber.toString()
                descr.text = book.descr

                view_btn.setOnClickListener {
                    val browserIntent =
                        Intent(Intent.ACTION_VIEW, Uri.parse(book.link))
                    startActivity(browserIntent)
                }
            }
        })

        mainViewModel.bookInfoFailureLiveData.observe(this, Observer { isFailed ->
            isFailed?.let {
                Toast.makeText(this, "Oops! something went wrong", Toast.LENGTH_SHORT).show()
            }
        })
    }
}