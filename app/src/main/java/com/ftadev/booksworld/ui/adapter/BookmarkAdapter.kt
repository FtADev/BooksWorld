package com.ftadev.booksworld.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.ftadev.booksworld.R
import com.ftadev.booksworld.model.BookImageModel
import com.ftadev.booksworld.model.BookModel
import com.ftadev.booksworld.ui.RoundedTransformation
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_book.view.*

class BookmarkAdapter : RecyclerView.Adapter<BookmarkAdapter.BookViewHolder>() {

    private var bookList: ArrayList<BookImageModel> = ArrayList()

    fun setBooks(books: List<BookImageModel>) {
        bookList.addAll(books)
        notifyDataSetChanged()
    }

    fun clearList() {
        bookList.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder =
        BookViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_book, parent, false))

    override fun getItemCount(): Int = bookList.size

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bindList(bookList[position])
    }

    class BookViewHolder(parent: View) : RecyclerView.ViewHolder(parent) {

        fun bindList(book: BookImageModel) {
            itemView.run {
                animation =
                    AnimationUtils.loadAnimation(context, R.anim.item_animation_fall_down)
                // We should explicitly add transform to make image's corner circular, unlike glide!
                Picasso.get().load(book.photo).transform(RoundedTransformation(20, 0)).into(image)
                setOnClickListener {
//                    val intent = Intent(context, BookActivity::class.java)
//                    intent.putExtra("ID", book.id)
//                    intent.putExtra("isComeFromDB", true)
//                    context.startActivity(intent)
                }
            }
        }
    }
}