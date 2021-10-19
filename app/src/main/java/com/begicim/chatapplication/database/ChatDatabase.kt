package com.begicim.chatapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.begicim.chatapplication.database.dao.MessageDao
import com.begicim.chatapplication.database.dao.UserDao
import com.begicim.chatapplication.database.table.Message
import com.begicim.chatapplication.database.table.User
import java.util.concurrent.Executors

@Database(entities = [User::class, Message::class], version = 1, exportSchema = false)
abstract class ChatDatabase : RoomDatabase() {

    abstract val messageDao: MessageDao
    abstract val userDao: UserDao

    companion object {
        @Volatile
        private var INSTANCE: ChatDatabase? = null

        fun getInstance(context: Context): ChatDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ChatDatabase::class.java,
                        "chat_history_database"
                    ).addCallback(object :RoomDatabase.Callback(){

                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)

                            insertDummyUsers(instance)
                        }
                    })
                        .fallbackToDestructiveMigration()
                        .build()
                }
                return instance
            }
        }

        private fun insertDummyUsers(instance: ChatDatabase?) {
            Executors.newSingleThreadExecutor().execute {
                instance?.let {
                    it.userDao.insertUser(User(name ="User1"))
                    it.userDao.insertUser(User(name ="User2"))
                }
            }
        }
    }
}