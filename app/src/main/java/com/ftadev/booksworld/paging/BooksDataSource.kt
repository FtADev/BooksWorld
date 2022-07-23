package com.ftadev.booksworld.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ftadev.booksworld.api.RetrofitManager
import com.ftadev.booksworld.model.BookImageModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class BooksDataSource(coroutineContext: CoroutineContext) :
    PagingSource<Int, BookImageModel>() {

    private val apiService = RetrofitManager.apiService
    private val job = Job()
    private val scope = CoroutineScope(coroutineContext + job)

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BookImageModel> {
        val startPageIndex = 1 // Using 0 causes error

        val page = params.key ?: startPageIndex
        val offset = page - 1 // Because my API starts at 0
        val limit = params.loadSize
        return try {
            val response = apiService.getBookImages(limit = limit, offset = offset)
            val books = response.body() ?: listOf()
            val prevKey = if (page == startPageIndex) null else page
            val nextKey = if (books.isEmpty()) null else page + 1

            LoadResult.Page(
                data = books,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, BookImageModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}