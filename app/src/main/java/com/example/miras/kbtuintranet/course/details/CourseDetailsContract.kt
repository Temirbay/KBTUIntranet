package com.example.miras.kbtuintranet.course.details

import com.example.miras.kbtuintranet.core.util.BasePresenter
import com.example.miras.kbtuintranet.core.util.BaseView

interface CourseDetailsContract {
    interface View : BaseView<Presenter> {
        fun hideDefaultImage()
        fun onResume()
        fun setAdapter (items : ArrayList<Any>)
        fun goToStudentDetails(uuid: String)
        fun showDefaultImage()
    }
    interface Presenter : BasePresenter<View> {
        fun readStudentByCourse (id : String)
        fun onItemClick(obj: Any)
    }
}