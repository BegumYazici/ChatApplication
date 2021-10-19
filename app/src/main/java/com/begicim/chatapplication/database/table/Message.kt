package com.begicim.chatapplication.database.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.begicim.chatapplication.util.DATE_FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "messages_table")
data class Message(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "message")
    val message: String,

    @ColumnInfo(name = "from_id")
    val fromID: Int,

    @ColumnInfo(name = "to_id")
    val toID: Int,

    @ColumnInfo(name = "message_date")
    var date: String = SimpleDateFormat(DATE_FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE).format(Calendar.getInstance().time)
)