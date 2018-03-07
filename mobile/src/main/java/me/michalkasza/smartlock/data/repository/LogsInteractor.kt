package me.michalkasza.smartlock.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Observable

class LogsInteractor {
    val db = FirebaseFirestore.getInstance().collection("logs")

    fun getLog(logId: String) : Observable<Log> {
        return Observable.create { subscriber ->
            db.document(logId).get().addOnSuccessListener({ logSnapshot->
                if(logSnapshot != null) {
                    val log = logSnapshot.toObject<Log>(Log::class.java)
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