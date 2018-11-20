package com.example.miras.kbtuintranet.course.list

import com.example.miras.kbtuintranet.core.util.BasePresenter
import com.example.miras.kbtuintranet.core.util.BaseView
import com.example.miras.kbtuintranet.entities.Course

interface CoursesListContract {
    interface View : BaseView<Presenter> {
        fun setCoursesAdapter (list : ArrayList<Any>)
        fun goToCourseDetails (uuid : String)
        fun goToMain()
        fun getStringArray (id : Int) : Array<String>?
        fun hideDefaultImage()
        fun showDefaultImage()
    }
    interface Presenter : BasePresenter<View> {
        fun readItems ()
        fun showStudents (course : Course)
        fun addCourse(course : Course)
        fun searchByText(text : String)
        fun onSpinnerItemClick(position: Int)
    }

}