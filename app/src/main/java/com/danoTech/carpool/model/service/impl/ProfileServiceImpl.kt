package com.danoTech.carpool.model.service.impl

import androidx.compose.ui.util.trace
import com.danoTech.carpool.model.service.AccountService
import com.danoTech.carpool.model.Profile
import com.danoTech.carpool.model.service.ProfileService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProfileServiceImpl
@Inject
constructor(
    private val fireStore: FirebaseFirestore,
    private val auth: AccountService
) : ProfileService {
    @OptIn(ExperimentalCoroutinesApi::class)
    override val profiles: Flow<List<Profile>>
        get() =
            auth.currentUser.flatMapLatest { user ->
                fireStore.collection(PROFILE_COLLECTION).whereEqualTo(USER_ID_FIELD, user.id)
                    .dataObjects()
            }

    override suspend fun getProfile(profileId: String): Profile? =
        fireStore.collection(PROFILE_COLLECTION).document(profileId).get().await().toObject()

    override suspend fun create(profile: Profile): String =
        trace(SAVE_PROFILE_TRACE) {
            val profileWithUserId =
                profile.copy(id = FirebaseAuth.getInstance().currentUser!!.email.toString())
            fireStore.collection(PROFILE_COLLECTION).document(profileWithUserId.userId)
                .set(profileWithUserId).await().toString()
        }

    override suspend fun update(profile: Profile): Unit =
        trace(UPDATE_PROFILE_TRACE) {
            val profileId = profile.userId
            fireStore.collection(PROFILE_COLLECTION).document(profileId).set(profile).await()
        }

    override suspend fun delete(profile: String) {
        fireStore.collection(PROFILE_COLLECTION).document(profile).delete().await()
    }

    companion object {
        private const val USER_ID_FIELD = "userId"
        private const val PROFILE_COLLECTION = "profile"
        private const val SAVE_PROFILE_TRACE = "saveProfile"
        private const val UPDATE_PROFILE_TRACE = "updateProfile"
    }
}