package com.example.miras.kbtuintranet

import com.example.miras.kbtuintranet.core.util.BaseListener
import com.example.miras.kbtuintranet.App.Companion.currentId
import com.example.miras.kbtuintranet.App.Companion.currentStudent
import com.example.miras.kbtuintranet.App.Companion.currentTeacher
import com.example.miras.kbtuintranet.App.Companion.mAuth
import com.example.miras.kbtuintranet.App.Companion.role
import com.example.miras.kbtuintranet.entities.Student
import com.example.miras.kbtuintranet.entities.Teacher
import com.example.miras.kbtuintranet.auth.login.LoginRepository
import com.example.miras.kbtuintranet.item.StudentRepository
import com.example.miras.kbtuintranet.item.TeacherRepository

class MainPresenter(private val repo : LoginRepository,
                    private val studentRepo : StudentRepository,
                    private val teacherRepo : TeacherRepository,
                    override var view : MainContract.View?)
    : BaseListener.onGetFinished, MainContract.Presenter {

    override fun start() {

    }

    override fun readObject() {
        studentRepo.readStudentsByUserId(this)
        teacherRepo.readTeachersByUserId(this)
    }

    override fun onGetFinished(obj: Any) {
        if (obj is Student) {
            role = "students"
            currentStudent = obj
            currentId = obj.uuid
            if (obj.imageUrl != "")
                view?.setImageHeader (obj.imageUrl)
            view?.setNameHeader (obj.name + " " + obj.surname)
        } else if (obj is Teacher){
            currentTeacher = obj
            currentId = obj.uuid
            role = "teachers"
            if (obj.imageUrl != "")
                view?.setImageHeader (obj.imageUrl)
            view?.setNameHeader (obj.name + " " + obj.surname)
        }
        view?.setEmailHeader (mAuth.currentUser?.email)
    }


    override fun onItemClick (obj : Any) {
        if (role == "teachers") {
            if (obj is Teacher)
                view?.goToTeacherDetails(obj.uuid)
            if (obj is Student)
                view?.goToStudentDetails(obj.uuid)
        }
        else if (role == "students" && obj is Student) {
            if (currentStudent.uuid == obj.uuid) {
                view?.goToStudentDetails(obj.uuid)
            }
            else view?.showMessage("Ошибка доступа")
        } else view?.showMessage("Ошибка доступа")
    }

    override fun logout () {
        repo.logout ()
        view?.goToLogin()
    }

    override fun goToMyCourses() {
        if (role == "teachers")
            view?.goToMyCoursesForTeacher(currentTeacher.uuid)
        else
            view?.goToMyCoursesForStudent(currentStudent.uuid)
    }

}