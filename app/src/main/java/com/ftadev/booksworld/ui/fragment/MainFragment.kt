package com.ftadev.booksworld.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ftadev.booksworld.databinding.FragmentMainBinding
import com.ftadev.booksworld.ui.adapter.BookAdapter
import com.ftadev.booksworld.ui.adapter.BookmarkAdapter
import com.ftadev.booksworld.ui.viewmodel.MainViewModel

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var bookAdapter: BookAdapter
    private lateinit var bookmarkAdapter: BookmarkAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        bookAdapter = BookAdapter()
        bookmarkAdapter = BookmarkAdapter()

        initialBookmarkList()
        loadBookmarks()

        initialBookList()
        loadBooks()

    }

    private fun initialBookList() {
        binding.rv.layoutManager = GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false)
        binding.rv.adapter = bookAdapter
    }

    private fun initialBookmarkList() {
        binding.myBookLayout.myBooks.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.myBookLayout.myBooks.adapter = bookmarkAdapter
    }

    private fun loadBooks() {
        mainViewModel.getBooks().observe(viewLifecycleOwner) {
            bookAdapter.submitList(it)
        }
    }

    private fun loadBookmarks() {
        mainViewModel.getBookmarkBooks()?.observe(viewLifecycleOwner) { bookList ->
            bookList?.let {
                bookmarkAdapter.submitList(it)
            }
        }
    }

}