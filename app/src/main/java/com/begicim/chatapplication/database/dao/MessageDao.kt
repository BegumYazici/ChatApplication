package com.begicim.chatapplication.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.begicim.chatapplication.database.table.Message

@Dao
interface MessageDao {
    @Insert
    fun insertMessage(message: Message)

    @Query("SELECT * FROM messages_table WHERE (from_id= :firstUserID AND to_id=:secondUserID) OR (from_id= :secondUserID AND to_id=:firstUserID) ORDER BY id DESC ")
    fun getMessages(firstUserID: Int, secondUserID: Int): List<Message>

    @Query("DELETE FROM messages_table")
    fun clearMessageTable()
}