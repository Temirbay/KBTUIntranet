package com.example.miras.kbtuintranet

import com.example.miras.kbtuintranet.core.util.BasePresenter
import com.example.miras.kbtuintranet.core.util.BaseView

interface MainContract {
    interface View : BaseView<Presenter> {

        fun goToProfile ()
        fun goToLogin ()
        fun onItemClick(view: android.view.View, obj: Any)
        fun goToStudentDetails(id : String)
        fun goToTeacherDetails(id : String)
        fun setImageHeader(imageUrl: String)
        fun setNameHeader(name: String)
        fun setEmailHeader(email: String?)
        fun goToMyCoursesForStudent(uuid: String)
        fun goToMyCoursesForTeacher(uuid: String)
    }
    interface Presenter : BasePresenter<View> {
        fun readObject()
        fun onItemClick (obj : Any)
        fun logout ()
        fun goToMyCourses()
    }
}