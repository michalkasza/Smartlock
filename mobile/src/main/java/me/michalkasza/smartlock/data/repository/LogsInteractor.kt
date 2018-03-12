package me.michalkasza.smartlock.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Observable
import me.michalkasza.smartlock.data.model.LogEntry

class LogsInteractor {
    val db = FirebaseFirestore.getInstance().collection("logs")

    fun getLog(logId: String) : Observable<LogEntry> {
        return Observable.create { subscriber ->
            db.document(logId).get().addOnSuccessListener({ logSnapshot->
                if(logSnapshot != null) {
                    val log = logSnapshot.toObject<LogEntry>(LogEntry::class.java)
                    subscriber.onNext(log)
                } else {
                    Log.e(TAG, "Error")
                }
            })
        }
    }

    companion object {
        val TAG = LogsInteractor::class.java.simpleName
    }
}