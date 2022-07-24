package com.ftadev.booksworld.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ftadev.booksworld.databinding.FragmentMainBinding
import com.ftadev.booksworld.ui.adapter.BookAdapter
import com.ftadev.booksworld.ui.adapter.BookmarkAdapter
import com.ftadev.booksworld.ui.viewmodel.MainViewModel
import kotlinx.coroutines.launch

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        bookAdapter = BookAdapter()
        bookmarkAdapter = BookmarkAdapter()

        initialBookmarkList()
        loadBookmarks()

        initialBookList()
        loadBooks()

    }

    private fun initialBookList() {
        binding.rv.layoutManager =
            GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false)
        binding.rv.adapter = bookAdapter
    }

    private fun initialBookmarkList() {
        binding.myBookLayout.myBooks.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.myBookLayout.myBooks.adapter = bookmarkAdapter
    }

    private fun loadBooks() {
        lifecycleScope.launch {
            mainViewModel.getBooks().observe(viewLifecycleOwner) {
                it?.let {
                    bookAdapter.submitData(lifecycle, it)
                }
            }
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