package com.begicim.chatapplication.ui.welcome

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.begicim.chatapplication.R
import com.begicim.chatapplication.database.ChatDatabase
import com.begicim.chatapplication.database.table.User
import kotlinx.android.synthetic.main.fragment_welcome.*

/*
 Dummy two users is added once db created, please take a look at  ChatDatabase
 */
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
            // we should only have 2 users
            if (it.size != EXPECTED_USER_COUNT)
                return@observe

            firstUserName = it[0].name
            secondUserName = it[1].name

            firstUserButton.text = firstUserName
            secondUserButton.text = secondUserName

            firstUser = it[0]
            secondUser = it[1]
        })

        firstUserButton.setOnClickListener {
            if (isUsersNotInitialised()) {
                showRestartAppToast()
                return@setOnClickListener
            }

            welcomeClickListener?.onUserClicked(firstUser, secondUser)
        }

        secondUserButton.setOnClickListener {
            if (isUsersNotInitialised()) {
                showRestartAppToast()
                return@setOnClickListener
            }

            welcomeClickListener?.onUserClicked(secondUser, firstUser)
        }
    }

    // It's a workaround when app first time opened this fragment might be visible before dummy users added tp db
    private fun isUsersNotInitialised(): Boolean {
        return !this::firstUser.isInitialized || !this::secondUser.isInitialized
    }

    private fun showRestartAppToast() {
        Toast.makeText(context, "Please restart app :)", Toast.LENGTH_LONG).show()
    }

    companion object {
        const val EXPECTED_USER_COUNT = 2
    }
}