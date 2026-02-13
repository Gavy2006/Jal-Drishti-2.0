package com.example.jaldrishti20

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore


class AuthViewModel : ViewModel() {

    private val auth = Firebase.auth
    private val firestore = Firebase.firestore

    private val _userProfile = MutableLiveData<UserModel?>()

    val userProfile: LiveData<UserModel?> = _userProfile

    val currentUser: FirebaseUser?
        get() = auth.currentUser

    fun login(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    fetchUserProfile() // Fetch profile on successful login
                    onResult(true, null)
                } else {
                    onResult(false, task.exception?.localizedMessage ?: "Login failed")
                }
            }
    }

    fun signup(email: String, name: String, password: String, onResult: (Boolean, String?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = task.result?.user?.uid
                    if (userId == null) {
                        onResult(false, "Failed to get user ID.")
                        return@addOnCompleteListener
                    }
                    val userModel = UserModel(name, email, userId)

                    firestore.collection("users").document(userId)
                        .set(userModel)
                        .addOnCompleteListener { dbTask ->
                            if (dbTask.isSuccessful) {
                                _userProfile.value = userModel // Update LiveData on signup
                                onResult(true, null)
                            } else {
                                onResult(false, dbTask.exception?.localizedMessage ?: "Firestore write failed")
                            }
                        }
                } else {
                    onResult(false, task.exception?.localizedMessage ?: "Signup failed")
                }
            }
    }

    fun fetchUserProfile() {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            _userProfile.value = null
            return
        }

        firestore.collection("users").document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    _userProfile.value = document.toObject(UserModel::class.java)
                } else {
                    _userProfile.value = null // Corrected this line
                }
            }
            .addOnFailureListener {
                _userProfile.value = null // Corrected this line
            }
    }


    fun logout() {
        auth.signOut()
        _userProfile.value = null // Clear profile on logout
    }
}
