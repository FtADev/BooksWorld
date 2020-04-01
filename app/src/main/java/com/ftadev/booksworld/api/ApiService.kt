package com.ftadev.booksworld.api

import com.ftadev.booksworld.model.BookModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("Book/")
    suspend fun getBooksBySubject() : Response<List<BookModel>>
}