package com.begicim.chatapplication.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.begicim.chatapplication.R
import com.begicim.chatapplication.ui.adapter.ChatAdapter
import com.begicim.chatapplication.ui.model.MessageUIModel


class ChatFragment : Fragment(R.layout.fragment_chat) {

    private lateinit var chatViewModel: ChatViewModel

    private val chatAdapter = ChatAdapter(
        listOf(
            MessageUIModel(
                "message1 dffdfdfd dffdfdfd dfdffd begum saat 12 yı gectı eyvah",
                "12.12.21",
                false,
                false
            ),
            MessageUIModel("message2-sender", "12.12.21", true, false),
            MessageUIModel("message3", "12.12.21", false, false),
            MessageUIModel("message4-sender", "12.12.21", true, true),
            MessageUIModel("message5", "12.12.21", false, false),
            MessageUIModel("message6-sender", "12.12.21", true, false),
            MessageUIModel("message3", "12.12.21", false, false),
            MessageUIModel("message4-sender", "12.12.21", true, true),
            MessageUIModel("message5", "12.12.21", false, false),
            MessageUIModel("message6-sender", "12.12.21", true, false),
            MessageUIModel("message3", "12.12.21", false, false),
            MessageUIModel("message4-sender", "12.12.21", true, true),
            MessageUIModel("message5", "12.12.21", false, false),
            MessageUIModel("message6-sender", "12.12.21", true, false),
            MessageUIModel("message5", "12.12.21", false, false),
            MessageUIModel("message6-sender", "12.12.21", true, false)
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        chatViewModel = ViewModelProvider(this).get(ChatViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chatRecyclerview = view.findViewById<RecyclerView>(R.id.chatRecyclerview)

        val linearLayoutManager = LinearLayoutManager(context).apply {
            reverseLayout = true
        }

        chatRecyclerview.layoutManager = linearLayoutManager
        chatRecyclerview.adapter = chatAdapter

        chatAdapter.notifyDataSetChanged()
    }

    companion object {
        const val CHAT_FRAGMENT_TAG = "Chat Fragment"
    }
}