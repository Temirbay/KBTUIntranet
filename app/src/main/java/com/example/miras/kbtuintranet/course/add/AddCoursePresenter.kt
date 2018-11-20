package com.example.miras.kbtuintranet.course.add

import com.example.miras.kbtuintranet.App.Companion.currentTeacher
import com.example.miras.kbtuintranet.entities.Course
import com.example.miras.kbtuintranet.course.CourseRepository

class AddCoursePresenter(private val repository : CourseRepository,
                         override var view : AddCourseContract.View?)
    : AddCourseContract.Presenter{

    override fun start() {

    }

    override fun insertItem (name : String, fall : String, credit : String, description : String) {
        if (validate (name, fall, credit, description)) {
            repository.insertItem(Course(name, fall, credit, description, currentTeacher.uuid))
            view?.goToCourses()
        }
    }

    override fun validate (name : String, fall : String, credit : String, description : String) : Boolean {
        if (name.isEmpty()) {
            view?.showMessage("Введите название")
            return false
        }
        if (fall.isEmpty()) {
            view?.showMessage("Введите семестр")
            return false
        }
        if (credit.isEmpty()) {
            view?.showMessage("Введите количество кредитов")
            return false
        }

        if (description.isEmpty()) {
            view?.showMessage("Введите описание")
            return false
        }
        return true
    }
}