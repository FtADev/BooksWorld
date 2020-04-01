package com.ftadev.booksworld.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.ftadev.booksworld.R
import com.ftadev.booksworld.model.BookImageModel
import com.squareup.picasso.Picasso

class BookAdapter : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    private var bookList: ArrayList<BookImageModel> = ArrayList()

    fun setBooks(books: List<BookImageModel>) {

        bookList.addAll(books)
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_book, parent, false)

        return BookViewHolder(view)
    }

    override fun getItemCount(): Int {

        return bookList.size
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {

        val book = bookList[position]
        Picasso.get().load(book.photo).into(holder.image);
    }

    class BookViewHolder(parent: View) : RecyclerView.ViewHolder(parent) {

        val image = parent.findViewById<ImageView>(R.id.image)

    }

}