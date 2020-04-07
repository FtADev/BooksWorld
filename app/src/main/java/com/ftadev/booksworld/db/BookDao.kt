package com.ftadev.booksworld.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ftadev.booksworld.model.BookModel

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addBook(book: BookModel)

    @Query("SELECT * from book ORDER BY id DESC")
    fun getAllBooks() : List<BookModel>

//    @Query("DELETE FROM book")
//    fun deleteBook(id: Int)
}