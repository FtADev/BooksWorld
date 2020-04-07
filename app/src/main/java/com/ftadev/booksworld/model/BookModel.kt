package com.ftadev.booksworld.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book")
data class BookModel(
    @PrimaryKey
    val id: Int,
    val author: String,
    val category: String?,
    val descr: String,
    val name: String,
    val photo: String,
    val publisher: String?,
    val rate: Double,
    val translator: String?,
    val pageNumber: Int?,
    val link: String?
)