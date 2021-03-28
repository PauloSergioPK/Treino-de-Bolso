package com.karasdecc.treinodebolso

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.karasdecc.treinodebolso.database.UserDatabaseManager
import com.karasdecc.treinodebolso.ui.viewmodel.TrainingViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MainApp : Application() {

    private val modules = module {
        viewModel { TrainingViewModel(db = get()) }
        single { Firebase.auth }
        single { Firebase.firestore }
        single { UserDatabaseManager(db = get())}
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApp)
            modules(modules)
        }
    }

}