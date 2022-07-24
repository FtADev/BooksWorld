package com.ftadev.booksworld.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.ftadev.booksworld.api.RetrofitManager
import com.ftadev.booksworld.model.BookImageModel
import com.ftadev.booksworld.model.BookModel
import com.ftadev.booksworld.paging.BooksDataSource
import kotlinx.coroutines.Dispatchers
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class MainRepository {

    private val apiService = RetrofitManager.apiService

    val bookSuccessLiveData = MutableLiveData<BookModel>()
    val bookFailureLiveData = MutableLiveData<Boolean>()

    fun getBooks(): LiveData<PagingData<BookImageModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                initialLoadSize = 10
            ),
            pagingSourceFactory = {
                BooksDataSource(Dispatchers.Default)
            }, initialKey = 1
        ).liveData
    }

    suspend fun getBookInfo(id: Int) {
        try {
            val response = apiService.getBookInfo(id)
            Log.d(TAG, "$response")

            if (response.isSuccessful) {
                Log.d(TAG, "SUCCESS")
                Log.d(TAG, "${response.body()}")
                bookSuccessLiveData.postValue(response.body())
            } else {
                Log.d(TAG, "FAILURE")
                Log.d(TAG, "${response.body()}")
                bookFailureLiveData.postValue(true)
            }

        } catch (e: UnknownHostException) {
            Log.e(TAG, "${e.message}")
            bookFailureLiveData.postValue(true)
        } catch (e: SocketTimeoutException) {
            Log.e(TAG, "${e.message}")
            bookFailureLiveData.postValue(true)
        } catch (e: Exception) {
            Log.e(TAG, "${e.message}")
            bookFailureLiveData.postValue(true)
        }
    }

    companion object {
        val TAG: String = MainRepository::class.java.simpleName
    }
}