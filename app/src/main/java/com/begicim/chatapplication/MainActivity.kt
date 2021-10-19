package com.begicim.chatapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.begicim.chatapplication.ui.ChatFragment
import com.begicim.chatapplication.ui.ChatFragment.Companion.CHAT_FRAGMENT_TAG

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val chatFragment : ChatFragment = if(savedInstanceState == null) {
            val chatFragment = ChatFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, chatFragment, CHAT_FRAGMENT_TAG)
                .commit()

            chatFragment
        } else {
            supportFragmentManager.findFragmentByTag(CHAT_FRAGMENT_TAG) as ChatFragment
        }

    }
}