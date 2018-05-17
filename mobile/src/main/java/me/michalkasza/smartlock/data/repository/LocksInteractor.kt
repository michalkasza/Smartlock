package me.michalkasza.smartlock.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import io.reactivex.Observable
import me.michalkasza.smartlock.data.model.Lock
import java.util.HashMap

class LocksInteractor {
    val db = FirebaseFirestore.getInstance().collection("locks")

    fun getLock(lockId: String) : Observable<Lock> {
        return Observable.create { subscriber ->
            db.document(lockId).addSnapshotListener({ lockSnapshot, firebaseFirestoreException ->
                if(lockSnapshot != null) {
                    lockSnapshot.toObject<Lock>(Lock::class.java)?.let { lock ->
                        subscriber.onNext(lock)
                    }
                } else {
                    Log.e(TAG, "Error")
                }
            })
        }
    }

    fun changeLockState(lockId: Lock, lockState: Boolean) {
        val data = HashMap<String, Any>()
        data.put("status", lockState)
        lockId.id.let { id -> db.document(id).set(data, SetOptions.merge()) }
    }

    companion object {
        val TAG = LocksInteractor::class.java.simpleName
    }
}