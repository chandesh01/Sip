package com.sip

import android.app.Application

import com.sip.data.SipDatabase

class App : Application() {

    val database by lazy {
        SipDatabase.getDatabase(this)
    }

    override fun onCreate() {

        super.onCreate()

        NotificationHelper(this)
            .createNotificationChannel()
    }
}