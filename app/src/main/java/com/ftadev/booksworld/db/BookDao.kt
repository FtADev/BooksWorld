package com.ftadev.booksworld.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ftadev.booksworld.model.BookImageModel
import com.ftadev.booksworld.model.BookModel

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addBook(book: BookModel)

    @Query("SELECT idBook, id, photo FROM book ORDER BY idBook desc")
    fun getAllBooks() : LiveData<List<BookImageModel>>

    @Query("SELECT * FROM book WHERE id = :id")
    fun getBook(id: Int): LiveData<BookModel>

}