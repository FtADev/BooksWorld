package com.ftadev.booksworld.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ftadev.booksworld.R
import com.ftadev.booksworld.model.BookImageModel
import com.ftadev.booksworld.paging.DiffUtilCallBack
import com.ftadev.booksworld.ui.RoundedTransformation
import com.ftadev.booksworld.ui.activity.BookActivity
import com.squareup.picasso.Picasso
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
                Picasso.get().load(book.photo).transform(RoundedTransformation(20, 0)).into(image)
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