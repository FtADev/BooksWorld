package com.ftadev.booksworld.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ftadev.booksworld.repository.MainRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val mainRepository =
        MainRepository()

    val usersSuccessLiveData = mainRepository.usersSuccessLiveData
    val usersFailureLiveData = mainRepository.usersFailureLiveData

    fun getUsers() {
        //this is coroutine viewmodel scope to call suspend fun of repo
        viewModelScope.launch { mainRepository.getBooks() }

    }
}