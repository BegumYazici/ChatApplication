package com.begicim.chatapplication.ui.chat.model

data class MessageUIModel(
    val message : String,
    val date : String,
    val isSender : Boolean,
    val shouldShowTheData : Boolean
)
