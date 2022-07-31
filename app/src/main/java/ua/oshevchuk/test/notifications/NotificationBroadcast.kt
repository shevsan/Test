package ua.oshevchuk.test.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ua.oshevchuk.test.data.databases.users.UsersRealmOperations
import ua.oshevchuk.test.ui.mainUsers.MainViewModel

/**
 * @author shevsan on 30.07.2022
 */
class NotificationBroadcast(private val viewModel: MainViewModel) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val id = intent!!.getStringExtra("userId")!!
        val changesCounter = intent.getIntExtra("changesCount", 0)
        CoroutineScope(Dispatchers.IO).launch {
            UsersRealmOperations().updateUser(id, changesCounter)
        }
        viewModel.getUsersWithChangesCounter()
    }
}