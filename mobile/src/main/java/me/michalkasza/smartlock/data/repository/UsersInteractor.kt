package me.michalkasza.smartlock.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Observable
import me.michalkasza.smartlock.data.model.User

class UsersInteractor {
    val db = FirebaseFirestore.getInstance().collection("users")

    fun getUser(userId: String) : Observable<User> {
        return Observable.create { subscriber ->
            db.document(userId).addSnapshotListener({ userSnapshot, firebaseFirestoreException ->
                if(userSnapshot != null) {
                    userSnapshot.toObject<User>(User::class.java)?.let { user ->
                        subscriber.onNext(user)
                    }
                } else {
                    Log.e(TAG, "Error")
                }
            })
        }
    }

    companion object {
        val TAG = UsersInteractor::class.java.simpleName
    }
}