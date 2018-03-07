package me.michalkasza.smartlock.data.model

class User {
    lateinit var id: String
    lateinit var name: String
    lateinit var surname: String
    lateinit var email: String
    lateinit var locksOwned: ArrayList<String>
    lateinit var locksGranted: ArrayList<String>

}