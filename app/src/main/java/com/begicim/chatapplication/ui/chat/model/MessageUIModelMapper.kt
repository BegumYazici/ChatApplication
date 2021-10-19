package com.begicim.chatapplication.ui.chat.model

import com.begicim.chatapplication.database.table.Message
import com.begicim.chatapplication.util.DATE_FORMAT_DAY_HOUR_MINUTE
import com.begicim.chatapplication.util.DATE_FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE
import java.text.SimpleDateFormat

class MessageUIModelMapper {

    fun toUIModel(fromID: Int, messageList: List<Message>) : List<MessageUIModel> {
        val messageUIList = mutableListOf<MessageUIModel>()

        for ((index, value) in messageList.withIndex()) {
            val previousMessageDate: String? =
                if (index == messageList.size - 1) {
                    null
                } else {
                    messageList[index + 1].date
                }

            val shouldShowDate = shouldShowDate(value.date, previousMessageDate)
            val formattedDate = formatDate(value.date)
            val isSender = fromID == value.fromID

            messageUIList.add(MessageUIModel(value.message, formattedDate, isSender,shouldShowDate))
        }

        return messageUIList
    }

    private fun shouldShowDate(dateString: String, previousDateString: String?): Boolean {
        return if (previousDateString == null) {
            true
        } else {
            val dateFormat = SimpleDateFormat(DATE_FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE)
            val date = dateFormat.parse(dateString)
            val previousDate = dateFormat.parse(previousDateString)

            val timeDifference = date.time - previousDate.time

            timeDifference > HOUR_TO_MILLIS
        }
    }

    private fun formatDate(dateString: String): String {
        val currentDateFormat = SimpleDateFormat(DATE_FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE)
        val date = currentDateFormat.parse(dateString)

        val dateScreenFormat = SimpleDateFormat(DATE_FORMAT_DAY_HOUR_MINUTE)
        val formattedDate = dateScreenFormat.format(date)

        return formattedDate
    }

    companion object{
        const val HOUR_TO_MILLIS = 3600000
    }
}