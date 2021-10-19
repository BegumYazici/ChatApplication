package com.begicim.chatapplication.ui.welcome

import com.begicim.chatapplication.database.table.User

interface WelcomeClickListener {

    fun onUserClicked(firstUser : User, secondUser: User)
}