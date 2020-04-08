package com.ftadev.booksworld.ui.activity

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
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
import com.ftadev.booksworld.ui.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.appbar2.*


class MainActivity : AppCompatActivity() {
    private val fragmentManager = supportFragmentManager
    private val firstFragment = AllBooksFragment()
    private val secondFragment = CategoryBooksFragment()

    private lateinit var mainViewModel: MainViewModel

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

//        val fragmentTransaction = fragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.book_list_fragment, firstFragment)
//        fragmentTransaction.commit()

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        bookAdapter = BookAdapter(this)
        bookmarkAdapter = BookmarkAdapter(this)

        //setting layout manager to recycler view and adapter
        my_books.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        my_books.adapter = bookmarkAdapter

        rv.layoutManager = GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false)
        rv.adapter = bookAdapter

        //before calling api register live data observer
        registerObservers()
        showBookmarkBook()

        //calling book list api
        mainViewModel.getBooksImage()
        mainViewModel.getBookmarkBooks()

//        chips_group.setOnCheckedChangeListener { chipGroup, i ->
//            val chip: Chip? = chipGroup.findViewById(i)
//            if (chip != null) {
//                if (chip.id == R.id.all_chip)
//                    allBookClick()
//                else
//                    categoryBookClick()
//            }
//        }
    }

    private fun registerObservers() {
        mainViewModel.booksSuccessLiveData.observe(this, Observer { bookList ->
            bookList?.let {
                bookAdapter.setBooks(it)
            }
        })

        mainViewModel.booksFailureLiveData.observe(this, Observer { isFailed ->
            isFailed?.let {
                Toast.makeText(this, "Oops! something went wrong", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showBookmarkBook() {
        mainViewModel.bookmarkListSuccessLiveData.observe(this, Observer { bookList ->
            Log.d("LENGTH", bookList.size.toString())
            bookList?.let {
                bookmarkAdapter.setBooks(it)
            }
        })

        mainViewModel.bookmarkListFailureLiveData.observe(this, Observer { isFailed ->
            isFailed?.let {
                Toast.makeText(this, "Oops! something went wrong", Toast.LENGTH_SHORT).show()
            }
        })
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
