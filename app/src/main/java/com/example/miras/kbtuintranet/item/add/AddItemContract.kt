package com.example.miras.kbtuintranet.item.add

import com.example.miras.kbtuintranet.core.util.BasePresenter
import com.example.miras.kbtuintranet.core.util.BaseView

interface AddItemContract {
    interface View : BaseView<Presenter> {

        fun changeStudentFormVisibility (status : Int)
        fun changeTeacherFormVisibility (status : Int)
        fun goToMain ()
    }
    interface Presenter : BasePresenter<View> {
        fun insertItem(name: String, surname: String, year: String)
        fun insertItem (name : String, surname : String)
    }
}