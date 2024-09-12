package com.danoTech.carpool.model.service

import com.danoTech.carpool.model.Car
import com.danoTech.carpool.model.Message
import com.danoTech.carpool.model.Profile
import kotlinx.coroutines.flow.Flow

interface StorageService {
    val reviews: Flow<List<Car>>
    suspend fun getReview(reviewId: String): Car?
    suspend fun save(car: Car): String
    suspend fun update(car: Car)
    suspend fun delete(carId: String)
    suspend fun getAvailableRide(destination: String): Flow<List<Car>>
    suspend fun getChatMessages(receiverId: String, userId: String): Flow<List<Message?>>
    suspend fun addChatMessage(message: Message)
    suspend fun getCars(): List<Car>
    suspend fun registerDriver(driver: Driver)
    suspend fun <User> requestRide(carpoolId: String, currentUser: Flow<User>)
    suspend fun getCarpool(carpoolId: String): Flow<Car?>
    suspend fun updateCarpool(updatedCarpool: Car)
    suspend fun addProfile(profile: Profile)
}