package dev.michalkasza.smartlock.data.model

import java.util.*
import kotlin.properties.Delegates

class Lock {
    var id: String by Delegates.notNull()
    var name: String? = null
    var status: Boolean by Delegates.notNull()
    var lastAccessTime: Long? = null
    var lastAccessUser: String? = null
    var ownerId: String by Delegates.notNull()
    var logs = ArrayList<String>()
    var accessList = ArrayList<String>()
}