package com.example.miras.kbtuintranet.course.my_list

import com.example.miras.kbtuintranet.core.util.BaseListener
import com.example.miras.kbtuintranet.entities.Course
import com.example.miras.kbtuintranet.course.CourseRepository
import com.example.miras.kbtuintranet.course.my_list.MyCoursesContract


class MyCoursesPresenter(private val repo : CourseRepository,
                         override var view : MyCoursesContract.View?)
    : MyCoursesContract.Presenter,
        BaseListener.onReadItemsFinished, BaseListener.onAddItemFinished {

    override fun start() {

    }

    override fun onAddFailed(message: String) {
        view?.showMessage(message)
    }

    override fun onAddSuccess() {
        view?.showMessage("Добавлено")
    }

    override fun onReadFinished(list: ArrayList<Any>) {
        if (list.isEmpty()) view?.showDefaultImage()
        view?.hideDefaultImage()
        view?.setCoursesAdapter(list)
        view?.hideProgress()
    }


    override fun readCoursesByStudent(uuid : String) {
        repo.readCoursesByStudent(uuid, this)
    }

    override fun readCourseByTeacher(uuid: String) {
        repo.readCoursesByTeacher(uuid, this)
    }

    override fun addCourse(course: Course) {
        repo.addCourse(course, this)
    }

}