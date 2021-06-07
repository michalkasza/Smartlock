package dev.michalkasza.smartlock.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import io.reactivex.Observable
import dev.michalkasza.smartlock.data.model.Lock
import java.util.HashMap

class LocksInteractor {
    private val db = FirebaseFirestore.getInstance().collection("locks")

    fun getLock(lockId: String) : Observable<Lock> {
        return Observable.create { subscriber ->
            db.document(lockId).addSnapshotListener { lockSnapshot, _ ->
                if(lockSnapshot != null) {
                    lockSnapshot.toObject<Lock>(Lock::class.java)?.let { lock ->
                        subscriber.onNext(lock)
                    }
                } else {
                    Log.e(TAG, "Error")
                }
            }
        }
    }

    fun changeLockState(lockId: Lock, lockState: Boolean) {
        val data = HashMap<String, Any>()
        data["status"] = lockState
        data["lastAccessTime"] = System.currentTimeMillis()
        data["lastAccessUser"] = "${UsersRepository.currentUser.value?.name} ${UsersRepository.currentUser.value?.surname}, ${UsersRepository.currentUser.value?.email}"
        lockId.id.let { id -> db.document(id).set(data, SetOptions.merge()) }
    }

    companion object {
        val TAG = LocksInteractor::class.java.simpleName
    }
}