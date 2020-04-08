package com.ftadev.booksworld.repository

import android.app.Application
import com.ftadev.booksworld.db.BookDao
import com.ftadev.booksworld.db.BookDatabase
import com.ftadev.booksworld.model.BookModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class DBRepository(application: Application) : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private var bookDao: BookDao?

    init {
        val db = BookDatabase.getDatabase(application)
        bookDao = db?.bookDao()
    }

    fun addBook(book: BookModel) {
        launch  { addBookBG(book) }
    }

    private suspend fun addBookBG(book: BookModel) {
        withContext(Dispatchers.IO) {
            bookDao?.addBook(book)
        }
    }

    fun getBook() = bookDao?.getAllBooks()

    fun getBook(id: Int) = bookDao?.getBook(id)


}