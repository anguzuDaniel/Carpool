package com.danoTech.carpool.model.service.impl

import androidx.compose.ui.util.trace
import com.danoTech.carpool.model.Car
import com.danoTech.carpool.model.service.AccountService
import com.danoTech.carpool.model.service.StorageService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class StorageServiceImpl
@Inject
constructor(private val firestore: FirebaseFirestore, private val auth: AccountService) :
    StorageService {

    @OptIn(ExperimentalCoroutinesApi::class)
    override val reviews: Flow<List<Car>>
        get() =
            auth.currentUser.flatMapLatest { user ->
                firestore.collection(REVIEW_COLLECTION).whereEqualTo(USER_ID_FIELD, user.id)
                    .dataObjects()
            }

    override suspend fun getReview(reviewId: String): Car? =
        firestore.collection(REVIEW_COLLECTION).document(reviewId).get().await().toObject()

    override suspend fun save(review: Car): String =
        trace(SAVE_TASK_TRACE) {
            val taskWithUserId =
                review.copy(id = FirebaseAuth.getInstance().currentUser!!.email.toString())
            firestore.collection(REVIEW_COLLECTION).add(taskWithUserId).await().id
        }

    override suspend fun update(review: Car): Unit =
        trace(UPDATE_TASK_TRACE) {
            firestore.collection(REVIEW_COLLECTION).document(review.id).set(review).await()
        }

    override suspend fun delete(reviewId: String) {
        firestore.collection(REVIEW_COLLECTION).document(reviewId).delete().await()
    }

    companion object {
        private const val USER_ID_FIELD = "userId"
        private const val REVIEW_COLLECTION = "reviews"
        private const val SAVE_TASK_TRACE = "saveReview"
        private const val UPDATE_TASK_TRACE = "updateReview"
    }
}