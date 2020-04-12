package com.ftadev.booksworld.paging

import androidx.recyclerview.widget.DiffUtil
import com.ftadev.booksworld.model.BookImageModel

class DiffUtilCallBack : DiffUtil.ItemCallback<BookImageModel>() {
    override fun areItemsTheSame(oldItem: BookImageModel, newItem: BookImageModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: BookImageModel, newItem: BookImageModel): Boolean {
        return oldItem.photo == newItem.photo
    }

}