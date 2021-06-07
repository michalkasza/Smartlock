package dev.michalkasza.smartlock.data.repository

import android.util.Log
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import io.reactivex.Observable
import dev.michalkasza.smartlock.data.model.Lock
import dev.michalkasza.smartlock.data.model.LogEntry
import dev.michalkasza.smartlock.data.model.User
import java.util.*

class LogsInteractor {
    private val db = FirebaseFirestore.getInstance().collection("logs")

    fun getLog(logId: String) : Observable<LogEntry> {
        return Observable.create { subscriber ->
            db.document(logId).addSnapshotListener { logSnapshot, _ ->
                if (logSnapshot != null) {
                    logSnapshot.toObject<LogEntry>(LogEntry::class.java)?.let { log ->
                        subscriber.onNext(log)
                    }
                } else {
                    Log.e(TAG, "Error")
                }
            }
        }
    }

    fun addLog(lockId: Lock, user: User) {
        val data = HashMap<String, Any?>()
        data["accessTime"] = Date()
        data["lockId"] = lockId.id
        data["userName"] = user.name + user.surname
        db.add(data).addOnSuccessListener(OnSuccessListener { docReference ->
            docReference?.let {
                val data2 = HashMap<String, Any>()
                val arr = lockId.logs
                arr.add(docReference.id)
                data2["logs"] = arr
                lockId.id.let { id -> FirebaseFirestore.getInstance().collection("locks").document(id).set(data2, SetOptions.merge()) }
            }
        })
    }

    companion object {
        private val TAG = LogsInteractor::class.java.simpleName
    }
}