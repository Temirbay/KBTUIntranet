package com.example.miras.kbtuintranet.auth.register

import com.example.miras.kbtuintranet.App
import com.example.miras.kbtuintranet.entities.Student
import com.example.miras.kbtuintranet.entities.Teacher
import com.example.miras.kbtuintranet.entities.User
import com.example.miras.kbtuintranet.auth.login.LoginRepository
import com.example.miras.kbtuintranet.item.StudentRepository
import com.example.miras.kbtuintranet.item.TeacherRepository
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import java.lang.Exception

class RegisterPresenter(private val loginRepo : LoginRepository,
                        private val studentRepo : StudentRepository,
                        private val teacherRepo : TeacherRepository,
                        override var view : RegisterContract.View?)
    : RegisterContract.Presenter, LoginRepository.onLoginFinished{

    override fun start() {

    }

    override fun onLoginFailed(exception: Exception) {
        view?.hideProgress()
        when (exception) {
            is FirebaseAuthInvalidCredentialsException -> view?.showMessage("Неправильный логин и пароль")
            is FirebaseAuthUserCollisionException -> view?.showMessage("Пользователь с таким логином не существует")
            else -> view?.showMessage(exception.message.toString())
        }
    }

    override fun onLoginSuccess() {
        view?.hideProgress()
        view?.onInsert()
    }


    override fun registerTeacher (username : String, password : String, name : String, surname : String) {
        if (validate(username, password, name, surname, "2018")) {
            view?.showProgress()
            loginRepo.register(User(username, password), this)
        }
    }

    override fun registerStudent (username : String, password : String, name : String, surname : String, year : String) {
        if (validate(username, password, name, surname, year)) {
            view?.showProgress()
            loginRepo.register(User(username, password), this)
        }
    }

    override fun insertTeacher (name : String, surname : String) {
        teacherRepo.insertTeacher(Teacher(name, surname, App.mAuth.currentUser!!.uid))
        view?.onSuccess()
    }

    override fun insertStudent (name : String, surname : String, year : String) {
        studentRepo.insertStudent(Student(name, surname, year, App.mAuth.currentUser!!.uid))
        view?.onSuccess()
    }

    private fun validate(username : String, password : String,
                         name : String, surname : String, year : String): Boolean {
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
        if (name.isEmpty()) {
            view?.showMessage("Введите имя")
            return false
        }
        if (surname.isEmpty()) {
            view?.showMessage("Введите фамилию")
            return false
        }
        if (year.isEmpty()) {
            view?.showMessage("Введите фамилию")
            return false
        }
        return true
    }
}