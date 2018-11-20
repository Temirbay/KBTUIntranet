package com.example.miras.kbtuintranet.course.list

import com.example.miras.kbtuintranet.core.util.BaseListener
import com.example.miras.kbtuintranet.entities.Course
import com.example.miras.kbtuintranet.R
import com.example.miras.kbtuintranet.course.CourseRepository

class CoursesListPresenter(private val repo : CourseRepository,
                           override var view : CoursesListContract.View?)
    : CoursesListContract.Presenter, BaseListener.onReadItemsFinished,
        BaseListener.onAddItemFinished{

    var items = ArrayList<Any>()

    override fun start() {

    }

    override fun onReadFinished(list: ArrayList<Any>) {
        items = list
        if (list.isEmpty()) view?.showDefaultImage()
        view?.hideDefaultImage()
        view?.setCoursesAdapter (list)
        view?.hideProgress()
    }

    override fun onAddSuccess() {
        view?.showMessage("Добавлено")
    }

    override fun onAddFailed(message: String) {
        view?.showMessage(message)
    }

    override fun readItems () {
        view?.showProgress()
        repo.readCourses(this)
    }

    override fun showStudents (course: Course) {
        view?.goToCourseDetails (course.uuid)
    }

    override fun addCourse (course: Course) {
        repo.addCourse(course, this)
    }

    override fun onSpinnerItemClick(position: Int) {
        val array = view?.getStringArray (R.array.spinner_list_array)?.getAsArrayList()
        val newItems = ArrayList<Any>()
        for (course in items) {
            if ((course as Course).fall == array?.get(position)
                    || array?.get(position) == "All")
                newItems.add(course)
        }
        view?.setCoursesAdapter(newItems)
        if (newItems.isEmpty()) view?.showDefaultImage()
        else view?.hideDefaultImage()
    }

    private fun Array<String>.getAsArrayList(): ArrayList<String> {
        val list = ArrayList<String>()
        this.forEach { it->list.add(it) }
        return list
    }

    override fun searchByText(text: String) {
        val newItems = ArrayList<Any>()
        for (item in items) {
            val course = item as Course
            if (course.name.contains(text, ignoreCase = true)) {
                newItems.add(course)
            }
        }
        view?.setCoursesAdapter(newItems)
        if (newItems.isEmpty()) view?.showDefaultImage()
        else view?.hideDefaultImage()
    }
}