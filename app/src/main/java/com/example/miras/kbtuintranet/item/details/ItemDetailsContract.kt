package com.example.miras.kbtuintranet.item.details

import android.content.Context
import android.net.Uri
import com.example.miras.kbtuintranet.core.util.BasePresenter
import com.example.miras.kbtuintranet.core.util.BaseView

interface ItemDetailsContract {
    interface View : BaseView<Presenter> {
        fun setImageProfile(url: String)
        fun getObject(): Any
        fun setObject(st: Any)
        fun goToCourseDetails(uuid: String)
        fun showFragment ()
        fun getContext () : Context
        fun goToMain()
        fun goToMyStudents(uuid: String)
    }
    interface Presenter : BasePresenter<View> {
        fun uploadFile(path : Uri)
        fun readTeacher (id : String)
        fun readStudent (id : String)
    }
}