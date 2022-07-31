package ua.oshevchuk.test.core.activity

import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.ContactsContract.Intents.Insert.ACTION
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import ua.oshevchuk.test.R
import ua.oshevchuk.test.databinding.ActivityMainBinding
import ua.oshevchuk.test.notifications.NotificationBroadcast
import ua.oshevchuk.test.ui.mainUsers.MainViewModel


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var handler: NotificationBroadcast

    override fun onStart() {
        super.onStart()
        registerReceiver(handler, IntentFilter(ACTION))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModelAndBroadcast()

    }

    private fun initViewModelAndBroadcast() {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        handler = NotificationBroadcast(viewModel)

    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(handler)
    }
}