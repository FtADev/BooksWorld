package com.ftadev.booksworld.model

data class BookModel(
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