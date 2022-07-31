package ua.oshevchuk.test

import android.app.Application
import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import dagger.hilt.android.HiltAndroidApp

/**
 * @author shevsan on 28.07.2022
 */

@HiltAndroidApp
class App:Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if(!task.isSuccessful){
                return@addOnCompleteListener
            }else {
                val token = task.result
                Log.d("mytag", token)
            }
        }
    }
}