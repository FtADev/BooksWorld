package com.ftadev.booksworld.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ftadev.booksworld.R
import com.ftadev.booksworld.model.BookImageModel
import com.ftadev.booksworld.ui.activity.BookActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_book.view.*

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
        holder.bindList(bookList[position])
    }

    class BookViewHolder(parent: View) : RecyclerView.ViewHolder(parent) {

        fun bindList(book: BookImageModel) {
            itemView.run {
                Picasso.get().load(book.photo).into(image)
            }
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, BookActivity::class.java)
                intent.putExtra("ID", book.id)
                itemView.context.startActivity(intent)
            }
        }

    }

}