package me.michalkasza.smartlock.data.repository

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import io.reactivex.Observable
import me.michalkasza.smartlock.data.model.AuthError
import me.michalkasza.smartlock.data.model.User

class UsersInteractor {
    val db = FirebaseFirestore.getInstance().collection("users")

    fun getUser(userId: String) : Observable<User> {
        return Observable.create { subscriber ->
            db.document(userId).addSnapshotListener({ userSnapshot, firestoreException ->
                if(userSnapshot != null && userSnapshot.exists()) {
                    userSnapshot.toObject<User>(User::class.java)?.let { user ->
                        user.id = userId
                        subscriber.onNext(user)
                    }
                } else {
                    subscriber.onError(AuthError.UserNotExistInFirestore())
                }
            })
        }
    }

    fun addUserEntryToFirestore(user: User) : Observable<User> {
        return Observable.create { subscriber ->
            db.document(user.id).set(user)
                    .addOnSuccessListener { subscriber.onNext(user) }
                    .addOnFailureListener { subscriber.onError(AuthError.RegistrationFailed()) }
        }
    }

    fun grantLockToUser(lockId: String, user: User) : Observable<User> {
        return Observable.create { subscriber ->
            val locksGrantedUpdate = user.locksGranted
            locksGrantedUpdate.add(lockId)
            user.locksGranted = locksGrantedUpdate
            db.document(user.id).update("locksGranted", locksGrantedUpdate)
                    .addOnSuccessListener { subscriber.onNext(user) }
                    .addOnFailureListener { e -> subscriber.onError(e) }
            FirebaseFirestore.getInstance().collection("locks").document(lockId).update("accessList", FieldValue.arrayUnion(user.id))
        }
    }

    fun getUserByEmail(email: String?) : Observable<User> {
        return Observable.create { subscriber ->
            db.whereEqualTo("email", email).get()
                    .addOnSuccessListener { usersMatched ->
                        usersMatched.forEach { userSnapshot ->
                            userSnapshot?.toObject<User>(User::class.java)?.let { user ->
                                user.id = userSnapshot.id
                                subscriber.onNext(user)
                            }
                        }
                    }
                    .addOnFailureListener {
                        subscriber.onError(it)
                    }
                    .addOnCompleteListener {
                        subscriber.onComplete()
                    }
        }
    }

    companion object {
        val TAG = UsersInteractor::class.java.simpleName
    }
}