package com.ftadev.booksworld.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ftadev.booksworld.repository.MainRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val mainRepository =
        MainRepository()

    val booksSuccessLiveData = mainRepository.booksImageSuccessLiveData
    val booksFailureLiveData = mainRepository.booksImageFailureLiveData
    val bookInfoSuccessLiveData = mainRepository.bookSuccessLiveData
    val bookInfoFailureLiveData = mainRepository.bookFailureLiveData

    fun getBooksImage() {
        //this is coroutine viewmodel scope to call suspend fun of repo
        viewModelScope.launch { mainRepository.getBooksImage() }

    }

    fun getBookInfo(id: Int) {
        viewModelScope.launch { mainRepository.getBookInfo(id) }

    }
}