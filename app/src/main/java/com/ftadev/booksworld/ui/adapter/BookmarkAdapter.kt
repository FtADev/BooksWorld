package com.ftadev.booksworld.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ftadev.booksworld.R
import com.ftadev.booksworld.model.BookImageModel
import com.ftadev.booksworld.paging.DiffUtilCallBack
import com.ftadev.booksworld.ui.RoundedTransformation
import com.ftadev.booksworld.ui.fragment.MainFragmentDirections
import com.squareup.picasso.Picasso

class BookmarkAdapter : ListAdapter<BookImageModel, BookmarkAdapter.BookViewHolder>(DiffUtilCallBack()) {

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
            // We should explicitly add transform to make image's corner circular, unlike glide!
            Picasso.get().load(item?.photo).transform(RoundedTransformation(20, 0)).into(holder.photo)
            setOnClickListener {
                item?.run {
                    navigateToDetail(id, it)
                }
            }
        }
    }

    private fun navigateToDetail(bookId: Int, view: View) {
        val action = MainFragmentDirections.actionBookDetail(bookId)
        view.findNavController().navigate(action)
    }
}