package com.example.miras.kbtuintranet.auth.login


import com.example.miras.kbtuintranet.entities.User
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import java.lang.Exception

class LoginPresenter(private val repo : LoginRepository,
                     override var view : LoginContract.View?)
    : LoginContract.Presenter, LoginRepository.onLoginFinished{

    override fun start() {

    }


    override fun onLoginSuccess() {
        view?.hideProgress()
        view?.onSuccess()
    }

    override fun onLoginFailed(exception: Exception) {
        view?.hideProgress()
        when (exception) {
            is FirebaseAuthWeakPasswordException -> view?.showMessage("Неправильный пароль")
            is FirebaseAuthInvalidCredentialsException -> view?.showMessage("Неправильный логин")
            is FirebaseAuthUserCollisionException -> view?.showMessage("Пользователь с таким логином не существует")
            else -> view?.showMessage(exception.message.toString())
        }
    }

    override fun login (username : String, password : String) {
        if (validate (username, password)) {
            view?.showProgress()
            repo.login(User(username, password), this)
        }
    }

    private fun validate(username : String, password : String): Boolean {
        if (username.isEmpty()) {
            view?.showMessage("Введите имя")
            return false
        }
        if (password.isEmpty()) {
            view?.showMessage("Введите пароль")
            return false
        }
        if (password.length < 8) {
            view?.showMessage("Пароль должен содержать минимум 8 символов")
            return false
        }
        return true
    }

}