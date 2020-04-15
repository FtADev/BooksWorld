package com.ftadev.booksworld.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation.findNavController
import com.ftadev.booksworld.R
import com.ftadev.booksworld.model.BookModel
import com.ftadev.booksworld.ui.RoundedTransformation
import com.ftadev.booksworld.ui.viewmodel.MainViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_book_detail.*

class BookDetailFragment : Fragment() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var resBook: BookModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_book_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val id = 1
        var isComeFromDB = false

        if (isComeFromDB) bookmark.setImageResource(R.drawable.bookmark_added)
        else bookmark.setImageResource(R.drawable.bookmark_add)

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        if (isComeFromDB) {
            showBookmarkBook(id)
        } else {
            registerObservers()
            mainViewModel.getBookInfo(id)
        }

        bookmark.setOnClickListener {
            isComeFromDB = !isComeFromDB
            if (isComeFromDB) {
                mainViewModel.addBookmark(resBook)
                bookmark.setImageResource(R.drawable.bookmark_added)
                Toast.makeText(context, "Added to Bookmark!", Toast.LENGTH_SHORT).show()
            } else {
                bookmark.setImageResource(R.drawable.bookmark_add)
                Toast.makeText(context, "Removed from Bookmark!", Toast.LENGTH_SHORT).show()
            }
        }


        back.setOnClickListener {
            findNavController(it).popBackStack()
        }
    }

    private fun registerObservers() {
        mainViewModel.bookInfoSuccessLiveData.observe(viewLifecycleOwner, Observer { book ->
            book?.let {
                resBook = it
                book_title.text = it.name
                author.text = it.author
                if (it.category != null)
                    category.text = it.category
                else
                    category.visibility = View.GONE
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

        mainViewModel.bookInfoFailureLiveData.observe(viewLifecycleOwner, Observer { isFailed ->
            isFailed?.let {
                Toast.makeText(context, "Oops! something went wrong", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showBookmarkBook(id: Int) {
        mainViewModel.getBookmarkInfo(id)?.observe(viewLifecycleOwner, Observer { book ->
            book?.let {
                resBook = it
                book_title.text = it.name
                author.text = it.author
                if (it.category != null)
                    category.text = it.category
                else
                    category.visibility = View.GONE
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
    }
}