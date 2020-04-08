package com.ftadev.booksworld.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ftadev.booksworld.model.BookModel
import com.ftadev.booksworld.repository.DBRepository
import com.ftadev.booksworld.repository.MainRepository
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val mainRepository = MainRepository()
    private var dbRepository: DBRepository = DBRepository(application)

    val booksSuccessLiveData = mainRepository.booksImageSuccessLiveData
    val booksFailureLiveData = mainRepository.booksImageFailureLiveData

    val bookInfoSuccessLiveData = mainRepository.bookSuccessLiveData
    val bookInfoFailureLiveData = mainRepository.bookFailureLiveData

    val bookmarkListSuccessLiveData = dbRepository.bookmarkListSuccessLiveData
    val bookmarkListFailureLiveData = dbRepository.bookmarkListFailureLiveData

    val bookmarkSuccessLiveData = dbRepository.bookmarkSuccessLiveData
    val bookmarkFailureLiveData = dbRepository.bookmarkFailureLiveData

    fun getBooksImage() {
        viewModelScope.launch { mainRepository.getBooksImage() }
    }

    fun getBookInfo(id: Int) {
        viewModelScope.launch { mainRepository.getBookInfo(id) }
    }

    fun getBookmarkBooks() {
        viewModelScope.launch { dbRepository.getBook() }
    }

    fun getBookmarkInfo(id: Int) {
        viewModelScope.launch { dbRepository.getBook(id) }
    }

    fun addBookmark(book: BookModel) {
        viewModelScope.launch { dbRepository.addBook(book) }
    }
}