package com.example.miras.kbtuintranet.auth.login

import com.example.miras.kbtuintranet.entities.User
import java.lang.Exception

interface LoginRepository {

    interface onLoginFinished {
        fun onLoginSuccess()
        fun onLoginFailed (exception : Exception)
    }


    fun login (user : User, listener: onLoginFinished)
    fun register (user : User, listener: onLoginFinished)
    fun logout ()
}