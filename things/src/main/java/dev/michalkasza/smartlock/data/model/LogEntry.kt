package dev.michalkasza.smartlock.data.model

import java.util.*

class LogEntry {
    lateinit var id: String
    lateinit var userId: String
    lateinit var lockId: String
    lateinit var accessTime: Date
}