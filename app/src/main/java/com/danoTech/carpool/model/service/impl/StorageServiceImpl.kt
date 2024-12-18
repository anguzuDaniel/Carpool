package com.danoTech.carpool.model.service.impl

import androidx.compose.ui.util.trace
import com.danoTech.carpool.model.Car
import com.danoTech.carpool.model.Message
import com.danoTech.carpool.model.Profile
import com.danoTech.carpool.model.service.AccountService
import com.danoTech.carpool.model.service.Driver
import com.danoTech.carpool.model.service.StorageService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.snapshots
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class StorageServiceImpl
@Inject
constructor(private val firestore: FirebaseFirestore, private val auth: AccountService) :
    StorageService {
    private val carQuery = firestore.collection(CAR_COLLECTION)

    @OptIn(ExperimentalCoroutinesApi::class)
    override val reviews: Flow<List<Car>>
        get() =
            auth.currentUser.flatMapLatest { user ->
                firestore.collection(REVIEW_COLLECTION).whereEqualTo(USER_ID_FIELD, user.id)
                    .dataObjects()
            }

    override suspend fun getReview(reviewId: String): Car? =
        firestore.collection(REVIEW_COLLECTION).document(reviewId).get().await().toObject()

    override suspend fun save(car: Car): String =
        trace(SAVE_TASK_TRACE) {
            val taskWithUserId =
                car.copy(id = FirebaseAuth.getInstance().currentUser!!.email.toString())
            firestore.collection(CAR_COLLECTION).add(taskWithUserId).await().id
        }

    override suspend fun update(car: Car): Unit =
        trace(UPDATE_TASK_TRACE) {
            firestore.collection(REVIEW_COLLECTION).document(car.id).set(car).await()
        }

    override suspend fun delete(carId: String) {
        firestore.collection(REVIEW_COLLECTION).document(carId).delete().await()
    }

    override suspend fun getAvailableRide(destination: String): Flow<List<Car>> {
        return firestore.collection(CAR_COLLECTION)
            .whereEqualTo(DESTINATION_FIELD, destination)
            .snapshots()
            .map { snapshot ->
                snapshot.documents.map { document ->
                    document.toObject(Car::class.java) ?: Car()
                }
            }
    }

    override suspend fun getChatMessages(receiverId: String, userId: String): Flow<List<Message?>> {
        val messagesRef = firestore.collection(MESSAGE_COLLECTION)
            .whereEqualTo(SENDER_ID_FIELD, userId)
            .whereEqualTo(RECEIVER_ID_FIELD, receiverId)
            .snapshots()

        return messagesRef.map { snapshot ->
            snapshot.documents.mapNotNull { document ->
                document.toObject(Message::class.java)
            }
        }
    }

    override suspend fun addChatMessage(message: Message) {
        val chatMessage = Message(
            conversationId = message.conversationId,
            senderId = message.senderId,
            receiverId = message.receiverId,
            content = message.content
        )

        firestore.collection(MESSAGE_COLLECTION).add(chatMessage).await()
    }

    override suspend fun getCars(): Flow<List<Car>> {
        return carQuery.snapshots()
            .map { snapshot ->
                snapshot.toObjects(Car::class.java)
            }
    }

    override suspend fun registerDriver(
        driver: Driver
    ) {
        firestore.collection(DRIVER_COLLECTION).document(driver.id).set(driver).await()
    }

    override suspend fun <User> requestRide(carpoolId: String, currentUser: Flow<User>) {
        val carpoolRef = firestore.collection(CAR_COLLECTION).document(carpoolId)

        // Fetch the current carpool data
        val carpool = carpoolRef.get().await().toObject(Car::class.java)

        carpool?.let {
            val updatedPassengers = it.passengers

            // Update the carpool with the new passenger
            carpoolRef.update("passengers", updatedPassengers).await()

            // Update available seats
            carpoolRef.update("seatsAvailable", it.seatsAvailable - 1).await()
        }
    }

    override suspend fun getCarpool(carpoolId: String): Flow<Car?> {
        return firestore.collection(CAR_COLLECTION).document(carpoolId)
            .snapshots()
            .map { snapshot -> snapshot.toObject(Car::class.java) }
    }

    override suspend fun updateCarpool(updatedCarpool: Car) {
        firestore.collection(CAR_COLLECTION).document(updatedCarpool.id)
            .set(updatedCarpool)
            .await()
    }

    override suspend fun addProfile(profile: Profile) {
        firestore.collection(PROFILE).add(profile).await()
    }

    private fun listenForCarUpdates(onCarsUpdated: (List<Car>) -> Unit) {
        carQuery.addSnapshotListener { snapshot, error ->
            if (error != null) {
                return@addSnapshotListener
            }

            if (snapshot != null) {
                val cars = snapshot.toObjects(Car::class.java)
                onCarsUpdated(cars) // Call the callback with the updated car list
            }
        }
    }

    companion object {
        private const val USER_ID_FIELD = "userId"
        private const val DESTINATION_FIELD = "destination"
        private const val REVIEW_COLLECTION = "reviews"
        private const val SAVE_TASK_TRACE = "saveCar"
        private const val UPDATE_TASK_TRACE = "updateReview"
        private const val MESSAGE_COLLECTION = "messages"
        private const val CAR_COLLECTION = "cars"
        private const val CONVERSATION_ID_FEIlD = "conversationId"
        private const val DRIVER_ID_FIELD = "driverId"
        private const val PROFILE = "profile"
        private const val SENDER_ID_FIELD = "senderId"
        private const val RECEIVER_ID_FIELD = "receiverId"
        private const val DRIVER_COLLECTION = "drivers"
    }
}