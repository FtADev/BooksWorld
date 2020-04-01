package com.ftadev.booksworld.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ftadev.booksworld.api.RetrofitManager
import com.ftadev.booksworld.model.BookModel
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class MainRepository {

    private val apiService = RetrofitManager.apiService

    val booksSuccessLiveData = MutableLiveData<List<BookModel>>()
    val booksFailureLiveData = MutableLiveData<Boolean>()

    /*
    this fun is suspend fun means it will execute in different thread
     */
    suspend fun getBooks() {

        try {

            //here api calling became so simple just 1 line of code
            //there is no callback needed

            val response = apiService.getBooksBySubject()

            Log.d(TAG, "$response")

            if (response.isSuccessful) {
                Log.d(TAG, "SUCCESS")
                Log.d(TAG, "${response.body()}")
                booksSuccessLiveData.postValue(response.body())

            } else {
                Log.d(TAG, "FAILURE")
                Log.d(TAG, "${response.body()}")
                booksFailureLiveData.postValue(true)
            }

        } catch (e: UnknownHostException) {
            Log.e(TAG, e.message)
            //this exception occurs when there is no internet connection or host is not available
            //so inform book that something went wrong
            booksFailureLiveData.postValue(true)
        } catch (e: SocketTimeoutException) {
            Log.e(TAG, e.message)
            //this exception occurs when time out will happen
            //so inform book that something went wrong
            booksFailureLiveData.postValue(true)
        } catch (e: Exception) {
            Log.e(TAG, e.message)
            //this is generic exception handling
            //so inform book that something went wrong
            booksFailureLiveData.postValue(true)
        }

    }

    companion object {
        val TAG = MainRepository::class.java.simpleName
    }
}