package org.example.bettr

import android.app.Application
import org.example.bettr.di.initKoin

class BettrApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}