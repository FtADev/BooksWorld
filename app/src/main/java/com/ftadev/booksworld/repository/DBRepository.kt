package com.ftadev.booksworld.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.ftadev.booksworld.db.BookDao
import com.ftadev.booksworld.db.BookDatabase
import com.ftadev.booksworld.model.BookModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class DBRepository(application: Application) /*: CoroutineScope*/ {


    val bookmarkSuccessLiveData = MutableLiveData<List<BookModel>>()
    val bookmarkFailureLiveData = MutableLiveData<Boolean>()

//    override val coroutineContext: CoroutineContext
//        get() = Dispatchers.Main

    private var bookDao: BookDao?

    init {
        val db = BookDatabase.getDatabase(application)
        bookDao = db?.bookDao()
    }


    suspend fun addBook(book: BookModel) {
        withContext(Dispatchers.IO) {
            bookDao?.addBook(book)
        }
    }

    suspend fun getBook() {
        withContext(Dispatchers.IO) {
            val response = bookDao?.getAllBooks()
            bookmarkSuccessLiveData.postValue(response)
        }
    }


}