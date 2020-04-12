package com.ftadev.booksworld.api

import com.ftadev.booksworld.model.BookImageModel
import com.ftadev.booksworld.model.BookModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("book/image/{limit}/{offset}")
    suspend fun getBookImages(@Path("offset") offset: Int = 0, @Path("limit") limit: Int = 10) : Response<List<BookImageModel>>

    @GET("book/info/{id}")
    suspend fun getBookInfo(@Path("id") id: Int) : Response<BookModel>

    @GET("book/search/{name}")
    suspend fun search(@Path("name") name: String) : Response<List<BookModel>>
}