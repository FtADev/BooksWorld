package com.ftadev.booksworld.db

import androidx.room.*
import com.ftadev.booksworld.model.BookModel

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addBook(book: BookModel)

    @Query("SELECT * from book ORDER BY id DESC")
    fun getAllBooks() : List<BookModel>

    @Query("SELECT * FROM book WHERE id = :id")
    fun getBook(id: Int): BookModel

}