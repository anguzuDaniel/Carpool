package com.danoTech.carpool.model.service

import com.danoTech.carpool.model.Profile
import kotlinx.coroutines.flow.Flow

interface ProfileService {
    val profiles: Flow<List<Profile>>

    suspend fun getProfile(profileId: String): Profile?
    suspend fun create(profile: Profile): String
    suspend fun update(profile: Profile)
    suspend fun delete(profile: String)
}