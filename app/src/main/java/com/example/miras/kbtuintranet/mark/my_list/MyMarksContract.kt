package com.example.miras.kbtuintranet.mark.my_list

import com.example.miras.kbtuintranet.core.util.BasePresenter
import com.example.miras.kbtuintranet.core.util.BaseView

interface MyMarksContract {
    interface View : BaseView<Presenter> {
        fun setAdapter(list: ArrayList<Any>)
        fun hideDefaultImage()
        fun showDefaultImage()
        fun setGpa(gpa: Double)
    }
    interface Presenter : BasePresenter<View> {
        fun readMarksByStudent()
    }
}