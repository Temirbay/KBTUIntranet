package com.example.miras.kbtuintranet.course.add

import com.example.miras.kbtuintranet.core.util.BasePresenter
import com.example.miras.kbtuintranet.core.util.BaseView

interface AddCourseContract {
    interface View : BaseView<Presenter> {
        fun goToCourses ()
    }
    interface Presenter : BasePresenter<View> {
        fun insertItem (name : String, fall : String, credit : String, description : String)
        fun validate (name : String, fall : String, credit : String, description : String) : Boolean
    }
}