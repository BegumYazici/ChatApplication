package com.begicim.chatapplication.ui.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.begicim.chatapplication.database.dao.MessageDao
import com.begicim.chatapplication.database.table.Message
import com.begicim.chatapplication.ui.chat.model.MessageUIModel
import com.begicim.chatapplication.ui.chat.model.MessageUIModelMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ChatViewModel(
    private val messageDao: MessageDao,
    private val firstUserID: Int,
    private val secondUserID: Int,
    private val uiMapper: MessageUIModelMapper
) : ViewModel() {

    private val _messagesList = MutableLiveData<List<MessageUIModel>>()
    val messagesList: LiveData<List<MessageUIModel>> = _messagesList

    private val job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + job)

    init {
        getMessageList()
    }

    fun sendMessage(messageText: String) {
        coroutineScope.launch {
            try {
                val message = Message(message = messageText, fromID = firstUserID, toID = secondUserID)
                messageDao.insertMessage(message)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        //I could've
        getMessageList()
    }


    private fun getMessageList() {
        coroutineScope.launch {
            try {
                val messageDbResponse = messageDao.getMessages(firstUserID, secondUserID)
                _messagesList.postValue(uiMapper.toUIModel(firstUserID, messageDbResponse))
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

class ChatViewModelFactory(
    private val messageDao: MessageDao,
    private val firstUserID: Int,
    private val secondUserID: Int,
    private val uiMapper: MessageUIModelMapper = MessageUIModelMapper()
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass == ChatViewModel::class.java) {
            return ChatViewModel(messageDao, firstUserID, secondUserID, uiMapper) as T
        }
        throw IllegalArgumentException("Could not found the class")
    }
}