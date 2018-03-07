package me.michalkasza.smartlock.data.model

import java.util.*
import kotlin.properties.Delegates

class Lock {
    lateinit var id: String
    lateinit var name: String
    var status: Boolean by Delegates.notNull()
    lateinit var lastAccess: Date
    lateinit var ownerId: String
    lateinit var logs: ArrayList<String>
}