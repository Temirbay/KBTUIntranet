package com.example.miras.kbtuintranet.profile.update

import com.example.miras.kbtuintranet.core.util.BasePresenter
import com.example.miras.kbtuintranet.core.util.BaseView

interface UpdateItemContract {
    interface View : BaseView<Presenter> {
        fun onSuccess ()
    }
    interface Presenter : BasePresenter<View> {
        fun updateItem (obj : Any)
    }
}