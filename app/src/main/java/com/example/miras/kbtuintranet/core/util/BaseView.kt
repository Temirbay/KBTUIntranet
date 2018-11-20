package com.example.miras.kbtuintranet.core.util

interface BaseView <out P : BasePresenter<*>> {
    val presenter : P

    fun showMessage(message : String)
    fun hideProgress ()
    fun showProgress ()
}