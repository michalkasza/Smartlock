package me.michalkasza.smartlock.data.model

class AuthError {
    class UserNotExistInFirestore : Throwable() {}
    class RegistrationFailed : Throwable() {}
}