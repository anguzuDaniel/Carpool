package com.danoTech.carpool.model.service

import com.danoTech.carpool.model.Car
import kotlinx.coroutines.flow.Flow

interface StorageService {
    val reviews: Flow<List<Car>>
    suspend fun getReview(reviewId: String): Car?
    suspend fun save(review: Car): String
    suspend fun update(review: Car)
    suspend fun delete(reviewId: String)
}