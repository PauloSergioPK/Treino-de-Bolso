package com.karasdecc.treinodebolso.database

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.karasdecc.treinodebolso.extensions.awaitRealtimeCollection
import com.karasdecc.treinodebolso.models.Exercise
import com.karasdecc.treinodebolso.models.Training
import com.karasdecc.treinodebolso.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception
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

    suspend fun getUserData(user: FirebaseUser): DatabaseResult<Boolean> =
        withContext(coroutineContext) {
            return@withContext runCatching {
                val trainingResult = getUserTrainings(user)
                if(trainingResult.isNotEmpty()){
                    DatabaseResult.Success(true)
                } else {
                    throw Exception("No trainings found")
                }
            }.getOrElse {
                it.printStackTrace()
                DatabaseResult.Failure(it)
            }

        }

    suspend fun getUserTrainings(user: FirebaseUser): List<Training> =
        withContext(coroutineContext) {
            return@withContext runCatching {
                val userSnapshots = db.collection(
                    FirebaseRefs.userTrainingRefs
                ).awaitRealtimeCollection() ?: throw Exception("Failed to retrieve user snapshots")

                val exercisesSnapshots = db.collection(
                    FirebaseRefs.EXERCISE
                ).awaitRealtimeCollection() ?: throw Exception("Failed to retrieve exercises snapshots")

                val trainings = mutableListOf<Training>()
                val map = mutableMapOf<String,Training>()
                exercisesSnapshots.forEach { exerciseSnapshot ->
                    val referenceExercise: Exercise = exerciseSnapshot.toObject() ?: throw Exception("Retrieved a malformed training")
                    userSnapshots.forEach { userSnapshot ->
                        val userTraining: Training = userSnapshot.toObject() ?: throw Exception("Retrieved a malformed training")
                        userTraining.exercises.forEach { exercise ->
                            if(exercise?.id == exerciseSnapshot.id){
                                val key = userTraining.title
                                val oldTraining = map[key]
                                if(oldTraining != null){
                                    oldTraining.exercisesList.add(referenceExercise)
                                    map[key] = oldTraining
                                } else {
                                    userTraining.exercisesList.add(referenceExercise)
                                    map[key] = userTraining
                                }
                            }
                        }
                    }
                }
                map.forEach{
                    trainings.add(it.value)
                }
                trainings
            }.getOrElse {
                it.printStackTrace()
                listOf()
            }
        }
}