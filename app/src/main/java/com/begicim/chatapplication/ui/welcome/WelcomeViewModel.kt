package com.begicim.chatapplication.ui.welcome

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.begicim.chatapplication.database.dao.UserDao
import com.begicim.chatapplication.database.table.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class WelcomeViewModel(private val userDao: UserDao) : ViewModel() {

    private val _userList = MutableLiveData<List<User>>()
    val userList: LiveData<List<User>> = _userList

    private val job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + job)

    init {
        getUsers()
    }

    private fun getUsers() {
        coroutineScope.launch {
            try {
                _userList.postValue(userDao.getUsers())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}

class WelcomeViewModelFactory(private val userDao: UserDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass == WelcomeViewModel::class.java) {
            return WelcomeViewModel(userDao) as T
        }
        throw IllegalArgumentException("$modelClass is not supported")
    }
}