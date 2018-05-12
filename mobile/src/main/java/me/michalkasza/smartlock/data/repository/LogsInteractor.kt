package me.michalkasza.smartlock.data.repository

import android.util.Log
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import io.reactivex.Observable
import me.michalkasza.smartlock.data.model.Lock
import me.michalkasza.smartlock.data.model.LogEntry
import me.michalkasza.smartlock.data.model.User
import java.util.*



class LogsInteractor {
    val db = FirebaseFirestore.getInstance().collection("logs")

    fun getLog(logId: String) : Observable<LogEntry> {
        return Observable.create { subscriber ->
            db.document(logId).addSnapshotListener({ logSnapshot, firebaseFirestoreException ->
                if(logSnapshot != null) {
                    logSnapshot.toObject<LogEntry>(LogEntry::class.java)?.let { log ->
                        subscriber.onNext(log)
                    }
                } else {
                    Log.e(TAG, "Error")
                }
            })
        }
    }

    fun addLog(lockId: Lock, userId: User) {
        val data = HashMap<String, Any?>()
        data.put("accessTime", Date())
        data.put("lockId", lockId.id)
        data.put("userId", userId.id)
        db.add(data).addOnSuccessListener(OnSuccessListener { docReference ->
            docReference?.let {
                val data2 = HashMap<String, Any>()
                val arr = lockId.logs
                arr.add(docReference.id)
                data2.put("logs", arr)
                lockId.id?.let { id -> FirebaseFirestore.getInstance().collection("locks").document(id).set(data2, SetOptions.merge()) }
            }
        })
    }

    companion object {
        val TAG = LogsInteractor::class.java.simpleName
    }
}