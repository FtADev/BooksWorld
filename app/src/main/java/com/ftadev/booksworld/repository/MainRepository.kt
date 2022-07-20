package com.ftadev.booksworld.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ftadev.booksworld.api.RetrofitManager
import com.ftadev.booksworld.model.BookModel
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class MainRepository {

    private val apiService = RetrofitManager.apiService

    val bookSuccessLiveData = MutableLiveData<BookModel>()
    val bookFailureLiveData = MutableLiveData<Boolean>()

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