package com.ftadev.booksworld.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.ftadev.booksworld.model.BookImageModel
import com.ftadev.booksworld.paging.BooksDataSource
import kotlinx.coroutines.Dispatchers

class BooksViewModel : ViewModel() {
    private var booksLiveData: LiveData<PagedList<BookImageModel>>

    init {
        val config = PagedList.Config.Builder()
            .setInitialLoadSizeHint(10)
            .setPageSize(10)
            .setEnablePlaceholders(false)
            .build()
        booksLiveData = initializedPagedListBuilder(config).build()
    }

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