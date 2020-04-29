package com.ftadev.booksworld.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.ftadev.booksworld.model.BookImageModel
import com.ftadev.booksworld.model.BookModel
import com.ftadev.booksworld.paging.BooksDataSource
import com.ftadev.booksworld.repository.DBRepository
import com.ftadev.booksworld.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val mainRepository = MainRepository()
    private var dbRepository: DBRepository = DBRepository(application)

    private var booksLiveData: LiveData<PagedList<BookImageModel>>

    val bookInfoSuccessLiveData = mainRepository.bookSuccessLiveData
    val bookInfoFailureLiveData = mainRepository.bookFailureLiveData

    init {
        val config = PagedList.Config.Builder()
            .setInitialLoadSizeHint(10)
            .setPageSize(10)
            .setEnablePlaceholders(false)
            .build()
        booksLiveData = initializedPagedListBuilder(config).build()
    }

    fun isMarked(id: Int): LiveData<Boolean>? = dbRepository.isMarked(id)

    fun getBookInfo(id: Int) {
        viewModelScope.launch { mainRepository.getBookInfo(id) }
    }

    fun getBookmarkBooks() = dbRepository.getBook()

    fun getBookmarkInfo(id: Int) = dbRepository.getBook(id)

    fun addBookmark(book: BookModel) = dbRepository.addBook(book)

    fun getBooks(): LiveData<PagedList<BookImageModel>> = booksLiveData

    private fun initializedPagedListBuilder(config: PagedList.Config):
            LivePagedListBuilder<Int, BookImageModel> {

        val dataSourceFactory = object : DataSource.Factory<Int, BookImageModel>() {
            override fun create(): DataSource<Int, BookImageModel> =
                BooksDataSource(Dispatchers.Default)
        }
        return LivePagedListBuilder(dataSourceFactory, config)
    }

}