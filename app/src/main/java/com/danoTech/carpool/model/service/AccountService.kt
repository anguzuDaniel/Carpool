package com.danoTech.carpool.model.service

import com.danoTech.carpool.model.Car
import com.danoTech.carpool.model.Profile
import com.danoTech.carpool.model.User
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.PhoneAuthCredential
import kotlinx.coroutines.flow.Flow

/**
 * Service for managing user accounts.
 * this interface is used by the AccountServiceImpl class
 * @see AccountServiceImpl for implementation details
 */
interface AccountService {
    val currentUserId: String
    val hasUser: Boolean
    val currentUser: Flow<User>
    suspend fun authenticate(email: String, password: String)
    suspend fun sendRecoveryEmail(email: String)
    suspend fun createAnonymousAccount()
    suspend fun createAccountWithEmailAndPassword(email: String, password: String)
    suspend fun createAccountWithNumberAndPassword(credential: PhoneAuthCredential)
    suspend fun initiatePhoneNumberVerification(phoneNumber: String)
    suspend fun checkUserExistsByEmail(email: String): Boolean
    suspend fun linkAccount(email: String, password: String)
    suspend fun changePassword(oobCode: String, newPassword: String): Boolean
    suspend fun deleteAccount()
    suspend fun signInWithCredential(credential: AuthCredential): Boolean
    suspend fun signOut()
}