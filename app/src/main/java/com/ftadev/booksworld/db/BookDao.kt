package com.ftadev.booksworld.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ftadev.booksworld.model.BookModel

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addBook(book: BookModel)

    @Query("SELECT * from book")
    fun getAllBooks() : LiveData<List<BookModel>>

    @Query("SELECT * FROM book WHERE id = :id")
    fun getBook(id: Int): LiveData<BookModel>

}