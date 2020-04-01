package com.ftadev.booksworld.api

import com.ftadev.booksworld.model.BookImageModel
import com.ftadev.booksworld.model.BookModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("book/")
    suspend fun getAllBooks() : Response<List<BookModel>>

    @GET("book/image")
    suspend fun getBookImages() : Response<List<BookImageModel>>

    @GET("book/{id}")
    suspend fun getBookInfo(@Path("id") id: Int) : Response<BookModel>
}