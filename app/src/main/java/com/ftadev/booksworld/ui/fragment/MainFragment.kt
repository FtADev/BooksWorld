package com.ftadev.booksworld.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ftadev.booksworld.R
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ftadev.booksworld.ui.adapter.BookAdapter
import com.ftadev.booksworld.ui.adapter.BookmarkAdapter
import com.ftadev.booksworld.ui.viewmodel.BooksViewModel
import com.ftadev.booksworld.ui.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.appbar2.*
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var booksViewModel: BooksViewModel

    private lateinit var bookAdapter: BookAdapter
    private lateinit var bookmarkAdapter: BookmarkAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        booksViewModel = ViewModelProviders.of(this).get(BooksViewModel::class.java)

        bookAdapter = BookAdapter()
        bookmarkAdapter = BookmarkAdapter()

        initialBookmarkList()
        loadBookmarks()

        initialBookList()
        loadBooks()

    }

    private fun initialBookList() {
        rv.layoutManager = GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false)
        rv.adapter = bookAdapter
        bookAdapter.itemClickListener = {
            findNavController().navigate(
                R.id.actionBookDetail
            )
        }
    }

    private fun initialBookmarkList() {
        my_books.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        my_books.adapter = bookmarkAdapter
    }

    private fun loadBooks() {
        booksViewModel.getBooks().observe(viewLifecycleOwner, Observer {
            bookAdapter.submitList(it)
        })
    }

    private fun loadBookmarks() {
        mainViewModel.getBookmarkBooks()?.observe(viewLifecycleOwner, Observer { bookList ->
            bookList?.let {
                bookmarkAdapter.clearList()
                bookmarkAdapter.setBooks(it)
            }
        })
    }

}