package com.example.miras.kbtuintranet.profile

import android.net.Uri
import com.example.miras.kbtuintranet.core.util.BasePresenter
import com.example.miras.kbtuintranet.core.util.BaseView

interface ProfileContract {
    interface View : BaseView<Presenter> {

        fun goToMain ()
        fun setObject (obj : Any)
        fun getObject () : Any
        fun showUpdateFragment ()
        fun setItems (obj : Any)
        fun setImageProfile (url : String)
        fun goToLogin ()
        fun goToMyMarks()
        fun changeVisibility()
        fun showForm()
    }
    interface Presenter : BasePresenter<View> {
        fun deleteItem (id : String)
        fun updateItem (obj : Any)
        fun uploadFile(path : Uri)
        fun goToMyMarks()
        fun readItem()
    }
}