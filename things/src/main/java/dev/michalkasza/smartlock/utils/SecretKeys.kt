package dev.michalkasza.smartlock.utils

object SecretKeys {
    init {
        System.loadLibrary("secret-keys")
    }

    external fun getDefaultAdminUUID(): String
}