package com.example.miras.kbtuintranet.item.add

import com.example.miras.kbtuintranet.App.Companion.role
import com.example.miras.kbtuintranet.entities.Student
import com.example.miras.kbtuintranet.entities.Teacher
import com.example.miras.kbtuintranet.item.StudentRepository
import com.example.miras.kbtuintranet.item.TeacherRepository
import java.lang.Character.isDigit

class AddItemPresenter(private val studentRepo: StudentRepository,
                       private val teacherRepo: TeacherRepository,
                       override var view : AddItemContract.View?)
    : AddItemContract.Presenter {

    var type : String?= null

    override fun start() {

    }


    override fun insertItem(name: String, surname: String, year: String) {
        val student = Student(name, surname, year)
        validate(student)
    }

    override fun insertItem (name : String, surname : String) {
        val teacher = Teacher(name, surname)
        validate(teacher)
    }

    fun onSuccess() {
        view?.hideProgress()
        view?.showMessage("Добавлено")
        view?.goToMain()
    }

    private fun onFailed(message: String) {
        view?.hideProgress()
        view?.showMessage(message)
        view?.goToMain()
    }

    private fun validate (obj: Any) {
        if (obj is Student) {
            val student : Student = obj
            if (!isNameValidated(student.name) || !isSurnameValidated(student.surname)
                || !isYearValidated(student.year))
                return
            view?.showProgress ()
            if (role == "teachers") {
                studentRepo.insertStudent(student)
                onSuccess()
            } else onFailed("Ошибка доступа")
        }

        else if (obj is Teacher) {
            val teacher : Teacher = obj
            if (!isNameValidated(teacher.name) || !isSurnameValidated(teacher.surname))
                return
            view?.showProgress ()
            if (role == "teachers") {
                teacherRepo.insertTeacher(teacher)
                onSuccess()
            } else onFailed("Ошибка доступа")
        }

    }

    private fun isNameValidated(name: String): Boolean {
        if (name.isEmpty()) {
            view?.showMessage("Введите имя")
            return false
        }
        return true
    }


    private fun isSurnameValidated(surname: String): Boolean {
        if (surname.isEmpty()) {
            view?.showMessage("Введите фамилию")
            return false
        }
        return true
    }

    private fun isYearValidated(year: String): Boolean {
        if (year.isEmpty()) {
            view?.showMessage("Введите курс")
            return false
        }

        for (char in year.toCharArray())
            if (!isDigit(char)) {
                view?.showMessage("Курс должен содержать только цифры")
                return false
            }

        return true
    }


}