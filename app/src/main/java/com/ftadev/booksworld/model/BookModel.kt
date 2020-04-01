package com.ftadev.booksworld.model

data class BookModel(
    val author: String,
    val category: String,
    val descr: String,
    val id: Int,
    val name: String,
    val photo: String,
    val publisher: String?,
    val rate: Double,
    val translator: String?
//    val pageNumber: Int?
)