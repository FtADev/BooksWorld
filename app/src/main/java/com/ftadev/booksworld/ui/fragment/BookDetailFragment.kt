package com.ftadev.booksworld.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.ftadev.booksworld.R
import com.ftadev.booksworld.databinding.FragmentBookDetailBinding
import com.ftadev.booksworld.model.BookModel
import com.ftadev.booksworld.ui.RoundedTransformation
import com.ftadev.booksworld.ui.viewmodel.MainViewModel
import com.squareup.picasso.Picasso

class BookDetailFragment : Fragment() {
    private lateinit var binding: FragmentBookDetailBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var resBook: BookModel
    private var isMarked = false

    private val args by navArgs<BookDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        isMarked(args.bookId)

        binding.back.setOnClickListener {
            findNavController(it).popBackStack()
        }
    }

    private fun isMarked(id: Int) = mainViewModel.isMarked(id)?.observe(viewLifecycleOwner) {
        isMarked = it

        if (isMarked) binding.bookmark.setImageResource(R.drawable.bookmark_added)
        else binding.bookmark.setImageResource(R.drawable.bookmark_add)

        if (isMarked) {
            showBookmarkBook(args.bookId)
        } else {
            registerObservers()
            mainViewModel.getBookInfo(args.bookId)
        }

        binding.bookmark.setOnClickListener {
            if (!isMarked) {
                mainViewModel.addBookmark(resBook)
                binding.bookmark.setImageResource(R.drawable.bookmark_added)
                Toast.makeText(context, "Added to Bookmark!", Toast.LENGTH_SHORT).show()
            } else {
                binding.bookmark.setImageResource(R.drawable.bookmark_add)
                Toast.makeText(context, "Removed from Bookmark!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun registerObservers() {
        mainViewModel.bookInfoSuccessLiveData.observe(viewLifecycleOwner) { book ->
            book?.let {
                binding.apply {
                    resBook = it
                    bookTitle.text = it.name
                    author.text = it.author
                    if (it.category != null)
                        category.text = it.category
                    else
                        category.visibility = View.GONE
                    Picasso.get().load(it.photo).transform(RoundedTransformation(20, 0)).into(photo)
//                    rb.rating = it.rate.toFloat()
                    pageNum.text = it.pageNumber.toString()
                    descr.text = it.descr

                    viewBtn.setOnClickListener {
                        val browserIntent =
                            Intent(Intent.ACTION_VIEW, Uri.parse(book.link))
                        startActivity(browserIntent)
                    }
                }
            }
        }

        mainViewModel.bookInfoFailureLiveData.observe(viewLifecycleOwner) { isFailed ->
            isFailed?.let {
                Toast.makeText(context, "Oops! something went wrong", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showBookmarkBook(id: Int) {
        mainViewModel.getBookmarkInfo(id)?.observe(viewLifecycleOwner) { book ->
            book?.let {
                binding.apply {
                    resBook = it
                    bookTitle.text = it.name
                    author.text = it.author
                    if (it.category != null)
                        category.text = it.category
                    else
                        category.visibility = View.GONE
                    Picasso.get().load(it.photo).transform(RoundedTransformation(20, 0)).into(photo)
//                    rb.rating = it.rate.toFloat()
                    pageNum.text = it.pageNumber.toString()
                    descr.text = it.descr

                    viewBtn.setOnClickListener {
                        val browserIntent =
                            Intent(Intent.ACTION_VIEW, Uri.parse(book.link))
                        startActivity(browserIntent)
                    }
                }
            }
        }
    }
}