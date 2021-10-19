package com.begicim.chatapplication.ui.adapter.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.begicim.chatapplication.R

sealed class ChatViewHolder(view : View) : RecyclerView.ViewHolder(view){

    class ChatSenderViewHolder(itemView: View) : ChatViewHolder(itemView) {
        val date = itemView.findViewById<TextView>(R.id.dateTextview)
        val message = itemView.findViewById<TextView>(R.id.senderMessageTextview)
    }

    class ChatReceiverViewHolder(itemView: View) : ChatViewHolder(itemView){
        val date = itemView.findViewById<TextView>(R.id.dateTextviewReceiver)
        val message = itemView.findViewById<TextView>(R.id.receiverMessageTextview)
    }
}
