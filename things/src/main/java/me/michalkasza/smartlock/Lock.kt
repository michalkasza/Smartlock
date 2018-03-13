package me.michalkasza.smartlock

import java.util.*
import kotlin.properties.Delegates

class Lock {
    var id: String? = null
    var name: String? = null
    var status: Boolean by Delegates.notNull()
    var lastAccessTime: Date? = null
    var lastAccessUser: String? = null
    var ownerId: String? = null
    var logs = ArrayList<String>()
    var accessList = ArrayList<String>()
}