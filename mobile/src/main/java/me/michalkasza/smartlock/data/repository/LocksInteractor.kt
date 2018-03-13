package me.michalkasza.smartlock.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Observable
import me.michalkasza.smartlock.data.model.Lock

class LocksInteractor {
    val db = FirebaseFirestore.getInstance().collection("locks")

    fun getLock(lockId: String) : Observable<Lock> {
        return Observable.create { subscriber ->
            db.document(lockId).addSnapshotListener({ lockSnapshot, firebaseFirestoreException ->
                if(lockSnapshot != null) {
                    val lock = lockSnapshot.toObject<Lock>(Lock::class.java)
                    subscriber.onNext(lock)
                } else {
                    Log.e(TAG, "Error")
                }
            })
        }
    }

    companion object {
        val TAG = LocksInteractor::class.java.simpleName
    }
}