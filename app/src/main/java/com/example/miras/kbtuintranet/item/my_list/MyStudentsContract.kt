package com.example.miras.kbtuintranet.item.my_list

import com.example.miras.kbtuintranet.core.util.BasePresenter
import com.example.miras.kbtuintranet.core.util.BaseView

interface MyStudentsContract {

    interface View : BaseView<Presenter> {
        fun hideDefaultImage()
        fun onResume()
        fun setAdapter (items : ArrayList<Any>)
        fun goToStudentDetails(uuid: String)
        fun showDefaultImage()
    }

    interface Presenter : BasePresenter<View> {
        fun readMarksByCourse(id: String)
        fun markStudent(id: String, value : String)
    }

}