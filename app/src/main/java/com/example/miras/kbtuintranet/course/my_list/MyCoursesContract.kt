package com.example.miras.kbtuintranet.course.my_list

import com.example.miras.kbtuintranet.core.util.BasePresenter
import com.example.miras.kbtuintranet.core.util.BaseView
import com.example.miras.kbtuintranet.entities.Course

interface MyCoursesContract {
    interface View : BaseView<Presenter> {
        fun onResume()
        fun hideDefaultImage()
        fun setCoursesAdapter (courses : ArrayList<Any>)
        fun showDefaultImage()
    }
    interface Presenter : BasePresenter<View> {

        fun readCoursesByStudent(uuid : String)
        fun readCourseByTeacher(uuid: String)
        fun addCourse(course: Course)
    }
}