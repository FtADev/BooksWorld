package com.ftadev.booksworld.paging

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.ftadev.booksworld.api.RetrofitManager
import com.ftadev.booksworld.model.BookImageModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class BooksDataSource(coroutineContext: CoroutineContext) :
    PageKeyedDataSource<Int, BookImageModel>() {

    private val apiService = RetrofitManager.apiService
    private val job = Job()
    private val scope = CoroutineScope(coroutineContext + job)

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, BookImageModel>
    ) {
        scope.launch {
            try {
                val response = apiService.getBookImages(limit = params.requestedLoadSize, offset = 0)
                when {
                    response.isSuccessful ->
                        callback.onResult(response.body() ?: listOf(), null, 0)
                }

            } catch (exception: Exception) {
                Log.e("PostsDataSource", "Failed to fetch data!")
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, BookImageModel>) {
        val page = params.key
        scope.launch {
            try {
                val response =
                    apiService.getBookImages(limit = params.requestedLoadSize, offset = page + 1)
                when {
                    response.isSuccessful ->
                        callback.onResult(response.body() ?: listOf(), page + 1)
                }
            } catch (exception: Exception) {
                Log.e("BooksDataSource", "Failed to fetch data!")
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, BookImageModel>) {}

    override fun invalidate() {
        super.invalidate()
        job.cancel()
    }
}