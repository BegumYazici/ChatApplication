package com.begicim.chatapplication.ui.chat

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.begicim.chatapplication.R
import com.begicim.chatapplication.database.ChatDatabase
import com.begicim.chatapplication.ui.chat.adapter.ChatAdapter
import kotlinx.android.synthetic.main.fragment_chat.*

class ChatFragment : Fragment(R.layout.fragment_chat) {

    private lateinit var chatViewModel: ChatViewModel
    private lateinit var chatViewModelFactory: ChatViewModelFactory

    private val chatAdapter = ChatAdapter()

    private lateinit var firstUser: String
    private lateinit var secondUser: String

    private var firstUserId: Int = 0
    private var secondUserId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //It's not good practice to force to open but sake of the time I did :)
        firstUser = arguments?.getString("firstUserName").toString()
        firstUserId = arguments?.getInt("firstUserID")!!

        secondUser = arguments?.getString("secondUserName").toString()
        secondUserId = arguments?.getInt("secondUserID")!!

        val messageDao = ChatDatabase.getInstance(requireContext()).messageDao

        chatViewModelFactory = ChatViewModelFactory(messageDao, firstUserId, secondUserId)
        chatViewModel = ViewModelProvider(this, chatViewModelFactory).get(ChatViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        testInformation.text = "From: $firstUser To: $secondUser"

        val linearLayoutManager = LinearLayoutManager(context).apply {
            reverseLayout = true
        }
        chatRecyclerview.layoutManager = linearLayoutManager

        chatRecyclerview.adapter = chatAdapter

        chatViewModel.messagesList.observe(viewLifecycleOwner, {
            chatAdapter.messageList = it
            chatAdapter.notifyDataSetChanged()
        })

        sendButton.setOnClickListener {
            val message = messageFieldTextview.text.toString()
            chatViewModel.sendMessage(message)

            messageFieldTextview.text.clear()
        }

        messageFieldTextview.doAfterTextChanged {
            it?.let {
                sendButton.isEnabled = it.isNotEmpty()
            }
        }
    }

    companion object {
        const val WELCOME_FRAGMENT_TAG = "Welcome Fragment"
    }
}