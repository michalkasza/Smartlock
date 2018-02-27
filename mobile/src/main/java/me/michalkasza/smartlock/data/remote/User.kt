package me.michalkasza.smartlock.data.remote

class User {
    lateinit var id: String
    lateinit var name: String
    lateinit var surname: String
    lateinit var email: String
    var locksOwned = HashMap<String, Any>()
    var locksGranted = HashMap<String, Any>()
}