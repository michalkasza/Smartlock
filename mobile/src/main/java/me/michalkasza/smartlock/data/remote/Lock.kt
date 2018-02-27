package me.michalkasza.smartlock.data.remote

class Lock {
    lateinit var id: String
    lateinit var name: String
    lateinit var status: Number
    lateinit var lastAccess: Number
    lateinit var ownerId: String
    var logs = HashMap<String, Any>()
}