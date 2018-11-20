package com.example.miras.kbtuintranet.core.util

interface BasePresenter<V> {

    var view : V?

    fun start ()

    fun stop () {
        view = null
    }
}