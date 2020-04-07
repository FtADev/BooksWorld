package com.ftadev.booksworld.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ftadev.booksworld.api.RetrofitManager
import com.ftadev.booksworld.model.BookImageModel
import com.ftadev.booksworld.model.BookModel
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class MainRepository {

    private val apiService = RetrofitManager.apiService

    val booksImageSuccessLiveData = MutableLiveData<List<BookImageModel>>()
    val booksImageFailureLiveData = MutableLiveData<Boolean>()

    val bookSuccessLiveData = MutableLiveData<BookModel>()
    val bookFailureLiveData = MutableLiveData<Boolean>()

    suspend fun getBooksImage() {
        try {
            val response = apiService.getBookImages()

            Log.d(TAG, "$response")

            if (response.isSuccessful) {
                Log.d(TAG, "SUCCESS")
                Log.d(TAG, "${response.body()}")
                booksImageSuccessLiveData.postValue(response.body())

            } else {
                Log.d(TAG, "FAILURE")
                Log.d(TAG, "${response.body()}")
                booksImageFailureLiveData.postValue(true)
            }

        } catch (e: UnknownHostException) {
            Log.e(TAG, e.message)
            //this exception occurs when there is no internet connection or host is not available
            booksImageFailureLiveData.postValue(true)
        } catch (e: SocketTimeoutException) {
            Log.e(TAG, e.message)
            //this exception occurs when time out will happen
            booksImageFailureLiveData.postValue(true)
        } catch (e: Exception) {
            Log.e(TAG, e.message)
            //this is generic exception handling
            booksImageFailureLiveData.postValue(true)
        }
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
                booksImageFailureLiveData.postValue(true)
            }

        } catch (e: UnknownHostException) {
            Log.e(TAG, e.message)
            booksImageFailureLiveData.postValue(true)
        } catch (e: SocketTimeoutException) {
            Log.e(TAG, e.message)
            booksImageFailureLiveData.postValue(true)
        } catch (e: Exception) {
            Log.e(TAG, e.message)
            booksImageFailureLiveData.postValue(true)
        }
    }

    companion object {
        val TAG = MainRepository::class.java.simpleName
    }
}