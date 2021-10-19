package com.begicim.chatapplication.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.begicim.chatapplication.database.table.User

@Dao
interface UserDao {
    @Insert
    fun insertUser(users: User)

    @Query("SELECT * FROM users_table")
    fun getUsers() : List<User>

    @Query("DELETE FROM users_table")
    fun clearUserTable()
}