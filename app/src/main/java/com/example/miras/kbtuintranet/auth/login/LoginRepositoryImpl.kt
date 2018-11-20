package com.example.miras.kbtuintranet.auth.login

import com.example.miras.kbtuintranet.App
import com.example.miras.kbtuintranet.auth.login.LoginRepository
import com.example.miras.kbtuintranet.entities.User

class LoginRepositoryImpl : LoginRepository {

    override fun login(user: User, listener: LoginRepository.onLoginFinished) {
        App.mAuth.signInWithEmailAndPassword(user.username, user.password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        listener.onLoginSuccess()
                    } else listener.onLoginFailed(it.exception!!)
                }
    }

    override fun register(user: User, listener: LoginRepository.onLoginFinished) {
        App.mAuth.createUserWithEmailAndPassword(user.username, user.password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        listener.onLoginSuccess()
                    } else listener.onLoginFailed(it.exception!!)
                }
    }

    override fun logout() {
        App.mAuth.signOut()
    }
}