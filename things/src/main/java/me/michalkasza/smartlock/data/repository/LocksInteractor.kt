package me.michalkasza.smartlock.data.repository

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import me.michalkasza.smartlock.data.model.Lock
import java.util.HashMap

class LocksInteractor {
    val db = FirebaseFirestore.getInstance().collection("locks")

    fun getLocks() : Observable<Lock> {
        return Observable.create { subscriber ->
            db.get()
                    .addOnSuccessListener { locksCollectionSnapshot ->
                        locksCollectionSnapshot.forEach { lockSnapshot ->
                            lockSnapshot?.toObject<Lock>(Lock::class.java)?.let { lock ->
                                lock.id = lockSnapshot.id
                                subscriber.onNext(lock)
                            }
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.e(TAG, "Error")
                    }
        }
    }

    fun addListener() : Observable<Lock> {
        return Observable.create { subscriber ->
            db.addSnapshotListener { locksListSnapshot, exc ->
                locksListSnapshot?.forEach { lockSnapshot ->
                    lockSnapshot?.toObject<Lock>(Lock::class.java)?.let { lock ->
                        lock.id = lockSnapshot.id
                        subscriber.onNext(lock)
                    }
                }
            }
        }
    }

    private fun addListener(sub: ObservableEmitter<Lock>, lockId: String) {
        db.document(lockId).addSnapshotListener { lockSnapshot, firebaseFirestoreException ->
            if(lockSnapshot != null) {
                lockSnapshot.toObject<Lock>(Lock::class.java)?.let { lock ->
                    lock.id = lockSnapshot.id
                    sub.onNext(lock)
                }
            } else {
                Log.e(TAG, "Error")
            }
        }
    }

    fun changeLockState(lockId: Lock, lockState: Boolean) {
        val data = HashMap<String, Any>()
        data.put("status", lockState)
        data.put("lastAccessTime", System.currentTimeMillis())
        data.put("lastAccessUser", "${UsersRepository.currentUser.value?.name} ${UsersRepository.currentUser.value?.surname}, ${UsersRepository.currentUser.value?.email}")
        lockId.id?.let { db.document(it).set(data, SetOptions.merge()) }
    }

    companion object {
        val TAG = LocksInteractor::class.java.simpleName
    }
}