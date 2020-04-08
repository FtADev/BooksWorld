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
import com.ftadev.booksworld.model.BookModel
import com.ftadev.booksworld.ui.RoundedTransformation
import com.ftadev.booksworld.ui.viewmodel.MainViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_book.*


class BookActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var resBook: BookModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        }

        setContentView(R.layout.activity_book)

        val id = intent.getIntExtra("ID", 0)
        var isComeFromDB = intent.getBooleanExtra("isComeFromDB", false)

        if (isComeFromDB) bookmark.setImageResource(R.drawable.bookmark_added)
        else bookmark.setImageResource(R.drawable.bookmark_add)

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        if (isComeFromDB) {
            showBookmarkBook()
            mainViewModel.getBookmarkInfo(id)
        } else {
            registerObservers()
            mainViewModel.getBookInfo(id)
        }

        bookmark.setOnClickListener {
            isComeFromDB = !isComeFromDB
            if (isComeFromDB) {
                mainViewModel.addBookmark(resBook)
                bookmark.setImageResource(R.drawable.bookmark_added)
                Toast.makeText(this, "Added to Bookmark!", Toast.LENGTH_SHORT).show()
            } else {
                bookmark.setImageResource(R.drawable.bookmark_add)
                Toast.makeText(this, "Removed from Bookmark!", Toast.LENGTH_SHORT).show()
            }
        }

        back.setOnClickListener {
            super.onBackPressed()
        }
    }

    private fun registerObservers() {
        mainViewModel.bookInfoSuccessLiveData.observe(this, Observer { book ->
            book?.let {
                resBook = it
                book_title.text = it.name
                author.text = it.author
                if (it.category != null)
                    category.text = it.category
                else
                    category.visibility = GONE
                Picasso.get().load(it.photo).transform(RoundedTransformation(20,0)).into(photo)
                rb.rating = it.rate.toFloat()
                page_num.text = it.pageNumber.toString()
                descr.text = it.descr

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

    private fun showBookmarkBook() {
        mainViewModel.bookmarkSuccessLiveData.observe(this, Observer { book ->
            book?.let {
                resBook = it
                book_title.text = it.name
                author.text = it.author
                if (it.category != null)
                    category.text = it.category
                else
                    category.visibility = GONE
                Picasso.get().load(it.photo).transform(RoundedTransformation(20,0)).into(photo)
                rb.rating = it.rate.toFloat()
                page_num.text = it.pageNumber.toString()
                descr.text = it.descr

                view_btn.setOnClickListener {
                    val browserIntent =
                        Intent(Intent.ACTION_VIEW, Uri.parse(book.link))
                    startActivity(browserIntent)
                }
            }
        })

        mainViewModel.bookmarkFailureLiveData.observe(this, Observer { isFailed ->
            isFailed?.let {
                Toast.makeText(this, "Oops! something went wrong", Toast.LENGTH_SHORT).show()
            }
        })
    }
}