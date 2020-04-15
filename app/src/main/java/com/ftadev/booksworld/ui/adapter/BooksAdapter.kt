package com.ftadev.booksworld.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ftadev.booksworld.R
import com.ftadev.booksworld.model.BookImageModel
import com.ftadev.booksworld.paging.DiffUtilCallBack
import kotlinx.android.synthetic.main.card_book.view.*

class BookAdapter(var itemClickListener: ((BookImageModel) -> Unit)? = null) :
    PagedListAdapter<BookImageModel, BookAdapter.BookViewHolder>(DiffUtilCallBack()) {

    class BookViewHolder(parent: View) : RecyclerView.ViewHolder(parent) {
        val photo: ImageView = parent.findViewById(R.id.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_book, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.run {
            animation =
                AnimationUtils.loadAnimation(context, R.anim.item_animation_fall_down)
            Glide.with(this).load(item?.photo).into(holder.photo) // It will have circular corner depends on its parent card xml
            setOnClickListener {
                if (item != null) itemClickListener?.invoke(item)
//                val intent = Intent(context, BookActivity::class.java)
//                    intent.putExtra("ID", item?.id)
//                    intent.putExtra("isComeFromDB", false)
//                    context.startActivity(intent)
            }
        }
    }
}