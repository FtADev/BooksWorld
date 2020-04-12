package com.ftadev.booksworld.ui.activity

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ftadev.booksworld.R
import com.ftadev.booksworld.ui.adapter.BookAdapter
import com.ftadev.booksworld.ui.adapter.BookmarkAdapter
import com.ftadev.booksworld.ui.fragment.AllBooksFragment
import com.ftadev.booksworld.ui.fragment.CategoryBooksFragment
import com.ftadev.booksworld.ui.viewmodel.BooksViewModel
import com.ftadev.booksworld.ui.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.appbar2.*


class MainActivity : AppCompatActivity() {
    private val fragmentManager = supportFragmentManager
    private val firstFragment = AllBooksFragment()
    private val secondFragment = CategoryBooksFragment()

    private lateinit var mainViewModel: MainViewModel
    private lateinit var booksViewModel: BooksViewModel

    private lateinit var bookAdapter: BookAdapter
    private lateinit var bookmarkAdapter: BookmarkAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // For gradient status bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        }

        setContentView(R.layout.activity_main)

//        initialFragment()

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        booksViewModel = ViewModelProviders.of(this).get(BooksViewModel::class.java)

        bookAdapter = BookAdapter()
        bookmarkAdapter = BookmarkAdapter(this)

        initialBookList()
        loadBooks()

        initialBookmarkList()
        loadBookmarks()
    }

    private fun initialBookList() {
        rv.layoutManager = GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false)
        rv.adapter = bookAdapter

    }

    private fun initialBookmarkList() {
        my_books.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        my_books.adapter = bookmarkAdapter
    }

    private fun loadBooks() {
        booksViewModel.getBooks().observe(this, Observer {
            bookAdapter.submitList(it)
        })
    }

    private fun loadBookmarks() {
        mainViewModel.getBookmarkBooks()?.observe(this, Observer { bookList ->
            bookList?.let {
                bookmarkAdapter.clearList()
                bookmarkAdapter.setBooks(it)
            }
        })
    }

    private fun initialFragment() {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.book_list_fragment, firstFragment)
        fragmentTransaction.commit()
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
