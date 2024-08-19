package com.danoTech.carpool.model.service

import com.danoTech.carpool.model.Car
import kotlinx.coroutines.flow.Flow

interface StorageService {
    val reviews: Flow<List<Car>>
    suspend fun getReview(reviewId: String): Car?
    suspend fun save(car: Car): String
    suspend fun update(car: Car)
    suspend fun delete(carId: String)
    suspend fun getAvailableRide(destination: String): Flow<List<List<Car>?>>
}