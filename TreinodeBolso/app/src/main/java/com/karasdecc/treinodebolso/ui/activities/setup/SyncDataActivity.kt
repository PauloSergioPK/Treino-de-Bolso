package com.karasdecc.treinodebolso.ui.activities.setup

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.karasdecc.treinodebolso.database.DatabaseResult
import com.karasdecc.treinodebolso.databinding.ActivitySyncDataBinding
import com.karasdecc.treinodebolso.ui.BaseBindingActivity
import com.karasdecc.treinodebolso.ui.activities.main.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import java.lang.IllegalStateException

@ExperimentalCoroutinesApi
class SyncDataActivity :
    BaseBindingActivity<ActivitySyncDataBinding>(ActivitySyncDataBinding::inflate) {

    private val auth: FirebaseAuth by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            initDatabase()
        }
    }

    private suspend fun initDatabase(): Unit = withContext(Dispatchers.IO) {
        val user = auth.currentUser
        val databaseResult = if (user != null) {
            userDatabaseManager.initDatabase(user)
        } else {
            DatabaseResult.Failure(IllegalStateException("Can't init database without a user"))
        }

        if (databaseResult is DatabaseResult.Success) {
            proceed()
        }
    }

    private fun proceed() {
        startActivity(Intent(this, MainActivity::class.java))
    }
}