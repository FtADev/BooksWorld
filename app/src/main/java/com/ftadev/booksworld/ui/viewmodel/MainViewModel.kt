package com.ftadev.booksworld.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ftadev.booksworld.model.BookImageModel
import com.ftadev.booksworld.model.BookModel
import com.ftadev.booksworld.repository.DBRepository
import com.ftadev.booksworld.repository.MainRepository
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val mainRepository = MainRepository()
    private var dbRepository: DBRepository = DBRepository(application)

    val bookInfoSuccessLiveData = mainRepository.bookSuccessLiveData
    val bookInfoFailureLiveData = mainRepository.bookFailureLiveData

    fun isMarked(id: Int): LiveData<Boolean>? = dbRepository.isMarked(id)

    fun getBookInfo(id: Int) {
        viewModelScope.launch { mainRepository.getBookInfo(id) }
    }

    fun getBookmarkBooks() = dbRepository.getBook()

    fun getBookmarkInfo(id: Int) = dbRepository.getBook(id)

    fun addBookmark(book: BookModel) = dbRepository.addBook(book)

    fun getBooks(): LiveData<PagingData<BookImageModel>> =
        mainRepository.getBooks().cachedIn(viewModelScope)
}