package com.begicim.chatapplication.ui.chat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.begicim.chatapplication.R
import com.begicim.chatapplication.ui.chat.adapter.viewholder.ChatViewHolder
import com.begicim.chatapplication.ui.chat.model.MessageUIModel

class ChatAdapter(var messageList: List<MessageUIModel> = mutableListOf()) :
    RecyclerView.Adapter<ChatViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return if (viewType == VIEW_TYPE_SENDER) {
            val senderView = layoutInflater.inflate(R.layout.item_message_sender, parent, false)
            ChatViewHolder.ChatSenderViewHolder(senderView)
        } else {
            val receiverView = layoutInflater.inflate(R.layout.item_message_receiver, parent, false)
            ChatViewHolder.ChatReceiverViewHolder(receiverView)
        }
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val messageModel = messageList[position]

        when(holder){
            is ChatViewHolder.ChatReceiverViewHolder ->{
                holder.date.text = messageModel.date
                holder.message.text = messageModel.message

               if(messageModel.shouldShowTheData) {
                   holder.date.visibility = View.VISIBLE
               }else{
                   holder.date.visibility = View.GONE
               }
            }
            is ChatViewHolder.ChatSenderViewHolder -> {
                holder.date.text = messageModel.date
                holder.message.text = messageModel.message

                if(messageModel.shouldShowTheData) {
                    holder.date.visibility = View.VISIBLE
                }else{
                    holder.date.visibility = View.GONE
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val messageModel = messageList[position]

        return if (messageModel.isSender) {
            VIEW_TYPE_SENDER
        } else {
            VIEW_TYPE_RECEIVER
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    companion object {
        const val VIEW_TYPE_SENDER = 100
        const val VIEW_TYPE_RECEIVER = 101
    }
}