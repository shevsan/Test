package ua.oshevchuk.test.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import ua.oshevchuk.test.ui.mainUsers.MainViewModel

/**
 * @author shevsan on 30.07.2022
 */
class NotificationBroadcast(private val viewModel: MainViewModel) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val id = intent!!.getStringExtra("userId")!!
        val changesCounter = intent.getIntExtra("changesCount", 0)
        viewModel.getUsersWithChangesCounter(changesCounter,id)
    }
}