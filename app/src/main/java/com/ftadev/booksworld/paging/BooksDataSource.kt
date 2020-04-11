package com.ftadev.booksworld.paging

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.ftadev.booksworld.model.BookImageModel
import com.ftadev.booksworld.repository.MainRepository
import kotlinx.coroutines.*

class BooksDataSource(private val repository: MainRepository,
                      private val scope: CoroutineScope) : PageKeyedDataSource<Int, BookImageModel>() {

    private var supervisorJob = SupervisorJob()
    private var retryQuery: (() -> Any)? = null // Keep reference of the last query (to be able to retry it if necessary)

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, BookImageModel>) {
        retryQuery = { loadInitial(params, callback) }
        executeQuery(1, params.requestedLoadSize) {
            callback.onResult(it, null, 2)
        }

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, BookImageModel>) {
        val page = params.key
        retryQuery = { loadAfter(params, callback) }
        executeQuery(page, params.requestedLoadSize) {
            callback.onResult(it, page + 1)
        }

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, BookImageModel>) { }

    private fun executeQuery(page: Int, perPage: Int, callback:(List<BookImageModel>) -> Unit) {
        scope.launch(getJobErrorHandler() + supervisorJob) {
            delay(200) // To handle user typing case
            val users = repository.getBooksImageWithPaging(page, perPage)
            retryQuery = null
            callback(users)
        }
    }
    private fun getJobErrorHandler() = CoroutineExceptionHandler { _, e ->
        Log.e(BooksDataSource::class.java.simpleName, "An error happened: $e")
    }

    override fun invalidate() {
        super.invalidate()
        scope.cancel()
    }

}