package com.danoTech.carpool.model.service.impl

import androidx.compose.ui.util.trace
import com.danoTech.carpool.model.Car
import com.danoTech.carpool.model.service.AccountService
import com.danoTech.carpool.model.User
import com.danoTech.carpool.model.service.Driver
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AccountServiceImpl @Inject constructor(private val auth: FirebaseAuth) : AccountService {
    override val currentUserId: String
        get() = auth.currentUser?.uid.orEmpty()

    override val hasUser: Boolean
        get() = auth.currentUser != null

    override val currentUser: Flow<User>
        get() = callbackFlow {
            val listener =
                FirebaseAuth.AuthStateListener { auth ->
                    this.trySend(auth.currentUser?.let { User(it.uid, it.isAnonymous) } ?: User())
                }
            auth.addAuthStateListener(listener)
            awaitClose { auth.removeAuthStateListener(listener) }
        }

    override suspend fun authenticate(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun sendRecoveryEmail(email: String) {
        auth.sendPasswordResetEmail(email).await()
    }

    override suspend fun createAnonymousAccount() {
        auth.signInAnonymously().await()
    }

    override suspend fun createAccountWithEmailAndPassword(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).await()
    }

    override suspend fun linkAccount(email: String, password: String): Unit =
        trace(LINK_ACCOUNT_TRACE) {
            val credential = EmailAuthProvider.getCredential(email, password)
            auth.currentUser!!.linkWithCredential(credential).await()
        }


    override suspend fun signInWithCredential(credential: AuthCredential): Boolean {
        return try {
            val authResult = FirebaseAuth.getInstance().signInWithCredential(credential).await()
            authResult.user != null
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun changePassword(oobCode: String, newPassword: String): Boolean {
        return suspendCoroutine { continuation ->
            auth.confirmPasswordReset(oobCode, newPassword)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Password reset was successful.
                        // You can provide feedback to the user.
                        // For example, navigate to the login screen.
                        continuation.resume(task.isSuccessful)
                    } else {
                        // Password reset failed.
                        val exception = task.exception
                        // Handle the error and provide feedback to the user.
                        continuation.resume(false)
                    }
                }
        }

    }

    // Function to check if a user account exists by email
    override suspend fun checkUserExistsByEmail(email: String): Boolean {
        return suspendCoroutine { continuation ->
            val auth = FirebaseAuth.getInstance()

            // Check if a user with the given email exists
            auth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // If task.isSuccessful, a user with the email exists
                        val signInMethods = task.result?.signInMethods
                        val userExists = !signInMethods.isNullOrEmpty()
                        continuation.resume(userExists)
                    } else {
                        // An error occurred
                        continuation.resume(false)
                    }
                }
        }
    }


    override suspend fun deleteAccount() {
        auth.currentUser!!.delete().await()
    }

    override suspend fun signOut() {
        auth.signOut()
    }

    companion object {
        private const val LINK_ACCOUNT_TRACE = "linkAccount"
    }
}