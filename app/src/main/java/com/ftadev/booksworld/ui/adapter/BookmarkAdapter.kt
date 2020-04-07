package com.ftadev.booksworld.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.ftadev.booksworld.R
import com.ftadev.booksworld.model.BookModel
import com.ftadev.booksworld.ui.RoundedTransformation
import com.ftadev.booksworld.ui.activity.BookActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_book.view.*

class BookmarkAdapter(private val context: Context) : RecyclerView.Adapter<BookmarkAdapter.BookViewHolder>() {

    private var bookList: ArrayList<BookModel> = ArrayList()

    fun setBooks(books: List<BookModel>) {

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
        holder.bindList(context, bookList[position])
    }

    class BookViewHolder(parent: View) : RecyclerView.ViewHolder(parent) {

        fun bindList(context: Context, book: BookModel) {
            itemView.animation =
                AnimationUtils.loadAnimation(context, R.anim.item_animation_fall_down)
            itemView.run {
                Picasso.get().load(book.photo).transform(RoundedTransformation(20, 0)).into(image)
            }
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, BookActivity::class.java)
                intent.putExtra("ID", book.id)
                itemView.context.startActivity(intent)
            }
        }

    }

}