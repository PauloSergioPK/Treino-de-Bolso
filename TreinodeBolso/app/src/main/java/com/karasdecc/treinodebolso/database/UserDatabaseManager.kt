package com.karasdecc.treinodebolso.database

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.karasdecc.treinodebolso.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
class UserDatabaseManager(
    private val db: FirebaseFirestore
) {
    private val coroutineContext: CoroutineContext = Dispatchers.IO

    suspend fun initDatabase(user: FirebaseUser): DatabaseResult<Boolean> =
        withContext(coroutineContext) {
            val userRef = db.collection(FirebaseRefs.USERS).document(user.uid)
            val batch = db.batch()
            runCatching {
                val localUser = User.createFromFirebaseUser(user)
                batch.set(userRef, localUser)
                batch.commit().await()
                DatabaseResult.Success(true)
            }.getOrElse {
                DatabaseResult.Failure(it)
            }
        }
}