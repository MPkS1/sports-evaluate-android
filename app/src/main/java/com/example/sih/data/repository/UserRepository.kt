package com.example.sih.data.repository

import com.example.sih.data.models.User
import com.example.sih.data.models.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserRepository {
    
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    // TODO: Integrate with Firebase Firestore for persistent storage
    // For now, using in-memory storage for demo purposes
    
    suspend fun saveUserProfile(uid: String, email: String, profile: UserProfile): Result<Unit> {
        return try {
            val user = User(
                uid = uid,
                email = email,
                profile = profile,
                isEmailVerified = true
            )
            _currentUser.value = user
            
            // TODO: Save to Firestore
            // FirebaseFirestore.getInstance()
            //     .collection("users")
            //     .document(uid)
            //     .set(user)
            //     .await()
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUserProfile(uid: String): Result<User?> {
        return try {
            // TODO: Fetch from Firestore
            // val doc = FirebaseFirestore.getInstance()
            //     .collection("users")
            //     .document(uid)
            //     .get()
            //     .await()
            // 
            // val user = doc.toObject<User>()
            
            // For demo, return current user if uid matches
            val user = _currentUser.value?.takeIf { it.uid == uid }
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateUserProfile(uid: String, profile: UserProfile): Result<Unit> {
        return try {
            val currentUser = _currentUser.value
            if (currentUser?.uid == uid) {
                _currentUser.value = currentUser.copy(profile = profile)
                
                // TODO: Update in Firestore
                // FirebaseFirestore.getInstance()
                //     .collection("users")
                //     .document(uid)
                //     .update("profile", profile)
                //     .await()
            }
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun setCurrentUser(user: User) {
        _currentUser.value = user
    }

    fun clearCurrentUser() {
        _currentUser.value = null
    }

    suspend fun checkIfProfileExists(uid: String): Boolean {
        return try {
            // TODO: Check Firestore
            // val doc = FirebaseFirestore.getInstance()
            //     .collection("users")
            //     .document(uid)
            //     .get()
            //     .await()
            // 
            // doc.exists() && doc.data?.get("profile") != null
            
            // For demo, check current user
            _currentUser.value?.uid == uid && _currentUser.value?.profile != null
        } catch (e: Exception) {
            false
        }
    }
}