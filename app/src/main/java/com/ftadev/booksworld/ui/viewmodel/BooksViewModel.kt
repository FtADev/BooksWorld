package com.ftadev.booksworld.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.ftadev.booksworld.model.BookImageModel
import com.ftadev.booksworld.paging.BooksDataSource
import com.ftadev.booksworld.repository.MainRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class BooksViewModel : ViewModel(){
    var postsLiveData  : LiveData<PagedList<BookImageModel>>
    private val repository = MainRepository()

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(10)
            .setEnablePlaceholders(false)
            .build()
        postsLiveData  = initializedPagedListBuilder(config).build()
    }

    fun getPosts(): LiveData<PagedList<BookImageModel>> = postsLiveData

    private fun initializedPagedListBuilder(config: PagedList.Config):
            LivePagedListBuilder<Int, BookImageModel> {

        val dataSourceFactory = object : DataSource.Factory<Int, BookImageModel>() {
            override fun create(): DataSource<Int, BookImageModel> {
                return BooksDataSource(repository , CoroutineScope(Dispatchers.Default))
            }
        }
        return LivePagedListBuilder(dataSourceFactory, config)
    }
}