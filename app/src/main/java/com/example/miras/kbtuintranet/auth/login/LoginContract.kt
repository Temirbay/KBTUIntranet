package com.example.miras.kbtuintranet.auth.login

import com.example.miras.kbtuintranet.core.util.BasePresenter
import com.example.miras.kbtuintranet.core.util.BaseView

interface LoginContract {
    interface View : BaseView<Presenter> {
        fun onSuccess()
        fun goToMain ()
    }
    interface Presenter : BasePresenter<View> {
        fun login (username : String, password : String)

    }
}