package com.example.miras.kbtuintranet.auth.register

import com.example.miras.kbtuintranet.core.util.BasePresenter
import com.example.miras.kbtuintranet.core.util.BaseView

interface RegisterContract {

    interface View : BaseView<Presenter> {

        fun onSuccess()
        fun onInsert ()
     }
    interface Presenter : BasePresenter<View> {
        fun registerTeacher (username : String, password : String, name : String, surname : String)
        fun registerStudent (username : String, password : String, name : String, surname : String, year : String)
        fun insertTeacher (name : String, surname : String)
        fun insertStudent (name : String, surname : String, year : String)
    }
}