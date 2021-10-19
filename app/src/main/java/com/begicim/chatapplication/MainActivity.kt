package com.begicim.chatapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.begicim.chatapplication.database.table.User
import com.begicim.chatapplication.ui.chat.ChatFragment
import com.begicim.chatapplication.ui.chat.ChatFragment.Companion.WELCOME_FRAGMENT_TAG
import com.begicim.chatapplication.ui.welcome.WelcomeClickListener
import com.begicim.chatapplication.ui.welcome.WelcomeFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val welcomeFragment : WelcomeFragment = if(savedInstanceState == null) {
               val welcomeFragment = WelcomeFragment()
               supportFragmentManager.beginTransaction()
                   .replace(R.id.fragment_container_view, welcomeFragment, WELCOME_FRAGMENT_TAG)
                   .commit()

               welcomeFragment
           } else {
               supportFragmentManager.findFragmentByTag(WELCOME_FRAGMENT_TAG) as WelcomeFragment
           }

        welcomeFragment.welcomeClickListener = object : WelcomeClickListener{
            override fun onUserClicked(firstUser: User, secondUser: User) {
                val chatFragment = ChatFragment()

                val bundle = Bundle()
                bundle.putString("firstUserName",firstUser.name)
                bundle.putInt("firstUserID", firstUser.userId)
                bundle.putString("secondUserName",secondUser.name)
                bundle.putInt("secondUserID", secondUser.userId)

                chatFragment.arguments = bundle

                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view,chatFragment)
                    .addToBackStack(null)
                    .commit()
            }
        }
    }
}