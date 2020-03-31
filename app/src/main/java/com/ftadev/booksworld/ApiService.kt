package com.ftadev.booksworld

import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("Book/")
    suspend fun getBooksBySubject() : Response<List<BookModel>>
}