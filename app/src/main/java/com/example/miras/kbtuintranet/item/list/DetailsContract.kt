package com.example.miras.kbtuintranet.item.list

import com.example.miras.kbtuintranet.core.util.BasePresenter
import com.example.miras.kbtuintranet.core.util.BaseView

interface DetailsContract {
    interface View : BaseView<Presenter> {

        fun hideDefaultImage()
        fun onResume()
        fun setAdapter (items : ArrayList<Any>)
        fun goToStudentDetails(uuid: String)
        fun showDefaultImage()
    }
    interface Presenter : BasePresenter<View> {
        fun readStudents ()
        fun readTeachers ()
        fun readStudentByCourse (id : String)
        fun onItemClick(obj: Any)
    }
}