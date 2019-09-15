package me.michalkasza.smartlock.data.model

import java.util.*

class LogEntry {
    lateinit var id: String
    lateinit var userName: String
    lateinit var lockId: String
    lateinit var accessTime: Date
}