package com.ftadev.booksworld.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ftadev.booksworld.repository.MainRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val mainRepository =
        MainRepository()

    val booksSuccessLiveData = mainRepository.booksSuccessLiveData
    val booksFailureLiveData = mainRepository.booksFailureLiveData

    fun getBooks() {
        //this is coroutine viewmodel scope to call suspend fun of repo
        viewModelScope.launch { mainRepository.getBooks() }

    }
}