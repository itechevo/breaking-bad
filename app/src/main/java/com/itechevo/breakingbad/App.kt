package com.itechevo.breakingbad

import android.app.Application
import com.itechevo.breakingbad.di.appModule
import com.itechevo.breakingbad.di.dataModule
import com.itechevo.breakingbad.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                listOf(
                    domainModule,
                    dataModule,
                    appModule
                )
            )
        }
    }
}