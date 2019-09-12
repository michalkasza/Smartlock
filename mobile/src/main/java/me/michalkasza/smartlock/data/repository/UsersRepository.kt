package me.michalkasza.smartlock.data.repository

import androidx.lifecycle.MutableLiveData
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import me.michalkasza.smartlock.data.model.Lock
import me.michalkasza.smartlock.data.model.User

object UsersRepository {
    val TAG = UsersRepository::class.java.simpleName
    val interactor = UsersInteractor()

    val currentUser = MutableLiveData<User>()
    val currentLockAccessedUsers = MutableLiveData<ArrayList<User>>()

    fun getCurrentUser() : Observable<User> {
        return Observable.create { subscriber ->
            FirebaseAuth.getInstance().currentUser?.let { firebaseUser ->
                interactor.getUser(firebaseUser.uid).observeOn(AndroidSchedulers.mainThread()).subscribeBy(
                        onNext = { user ->
                            currentUser.value = user
                            subscriber.onNext(user)
                        },
                        onError = { e ->
                            subscriber.onError(e)
                        }

                )
            }
        }
    }

    fun getUsers(usersId: ArrayList<String>) {
        currentLockAccessedUsers.value = ArrayList()
            usersId.forEach { userId -> interactor.getUser(userId).observeOn(AndroidSchedulers.mainThread()).subscribeBy(
                    onNext = { user ->
                        val tempUsers = arrayListOf<User>()
                        currentLockAccessedUsers.value?.let { tempUsers.addAll(it) }
                        user.id = userId
                        tempUsers.forEach {
                            if(it.id == userId) {
                                tempUsers.remove(it)
                            }
                        }
                        tempUsers.add(user)
                        currentLockAccessedUsers.value = tempUsers
                    },
                    onError = { Log.e(TAG, "Error") }
            )
        }
    }

    fun registerUser(userData: FirebaseUser?) : Observable<User> {
        return Observable.create { subscriber ->
            val user = User()
            userData?.uid?.let { id -> user.id = id }
            userData?.email?.let { email -> user.email = email }
            userData?.displayName?.let { name -> user.name = name; user.surname = name }
            user.locksGranted = arrayListOf()
            user.locksOwned = arrayListOf()
            interactor.addUserEntryToFirestore(user).observeOn(AndroidSchedulers.mainThread()).subscribeBy(
                    onNext = { registeredUser ->
                        currentUser.value = registeredUser
                        subscriber.onNext(registeredUser)
                    },
                    onError = { e -> subscriber.onError(e)}
            )
        }
    }

    fun getUserByEmail(email: String?) : Observable<User> {
        return Observable.create { subscriber ->
            interactor.getUserByEmail(email).observeOn(AndroidSchedulers.mainThread()).subscribeBy(
                    onNext = { user ->
                        subscriber.onNext(user)
                    },
                    onError = { e -> subscriber.onError(e) }
            )
        }
    }

    fun grantLockToUser(lockId: String?, user: User?) {
        lockId?.let { lockId -> user?.let { user ->
            interactor.grantLockToUser(lockId, user).observeOn(AndroidSchedulers.mainThread()).subscribeBy(
                    onNext = { grantedUser ->

                    },
                    onError = { Log.e(TAG, "Error") }
            )
        } }

    }
}