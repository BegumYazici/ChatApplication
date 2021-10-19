package com.begicim.chatapplication.ui.welcome

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.begicim.chatapplication.R
import com.begicim.chatapplication.database.ChatDatabase
import com.begicim.chatapplication.database.table.User
import kotlinx.android.synthetic.main.fragment_welcome.*

class WelcomeFragment : Fragment(R.layout.fragment_welcome) {

    lateinit var welcomeViewModel: WelcomeViewModel
    lateinit var welcomeViewModelFactory: WelcomeViewModelFactory

    private lateinit var firstUser: User
    private lateinit var secondUser: User

    private lateinit var firstUserName: String
    private lateinit var secondUserName: String

    var welcomeClickListener: WelcomeClickListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userDao = ChatDatabase.getInstance(requireContext()).userDao

        welcomeViewModelFactory = WelcomeViewModelFactory(userDao)
        welcomeViewModel =
            ViewModelProvider(this, welcomeViewModelFactory).get(WelcomeViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        welcomeViewModel.userList.observe(viewLifecycleOwner, {
            firstUserName = it[0].name
            secondUserName = it[1].name

            firstUserButton.text = firstUserName
            secondUserButton.text = secondUserName

            firstUser = it[0]
            secondUser = it[1]
        })

        firstUserButton.setOnClickListener {
            welcomeClickListener?.onUserClicked(firstUser, secondUser)
        }

        secondUserButton.setOnClickListener {
            welcomeClickListener?.onUserClicked(secondUser, firstUser)
        }
    }
}