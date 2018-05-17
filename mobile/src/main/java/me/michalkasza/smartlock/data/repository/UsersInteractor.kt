package me.michalkasza.smartlock.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import me.michalkasza.smartlock.data.model.User

class UsersInteractor {
    val db = FirebaseFirestore.getInstance().collection("users")

    fun getUser(userId: String) : Observable<User> {
        return Observable.create { subscriber ->
            db.document(userId).addSnapshotListener({ userSnapshot, firebaseFirestoreException ->
                if(userSnapshot != null) {
                    userSnapshot.toObject<User>(User::class.java)?.let { user ->
                        user.id = userId
                        subscriber.onNext(user)
                    }
                } else {
                    Log.e(TAG, "Error")
                }
            })
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
        }
    }

    companion object {
        val TAG = UsersInteractor::class.java.simpleName
    }
}