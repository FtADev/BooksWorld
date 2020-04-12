package com.ftadev.booksworld.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ftadev.booksworld.R
import com.ftadev.booksworld.model.BookImageModel
import com.ftadev.booksworld.paging.DiffUtilCallBack
import com.ftadev.booksworld.ui.activity.BookActivity
import kotlinx.android.synthetic.main.card_book.view.*

class BookAdapter : PagedListAdapter<BookImageModel, BookAdapter.BookViewHolder>(
    DiffUtilCallBack()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_book, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        getItem(position)?.let { holder.bindBook(it) }
    }

    class BookViewHolder(parent: View) : RecyclerView.ViewHolder(parent) {

        fun bindBook(book: BookImageModel) {
            itemView.run {
                animation =
                    AnimationUtils.loadAnimation(context, R.anim.item_animation_fall_down)
                Glide.with(this).load(book.photo).into(image) // It will have circular corner depends on its parent card xml
                setOnClickListener {
                    val intent = Intent(context, BookActivity::class.java)
                    intent.putExtra("ID", book.id)
                    intent.putExtra("isComeFromDB", false)
                    context.startActivity(intent)
                }
            }
        }
    }
}