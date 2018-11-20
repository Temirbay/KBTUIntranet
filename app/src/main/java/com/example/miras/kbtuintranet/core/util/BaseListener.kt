package com.example.miras.kbtuintranet.core.util

import java.lang.Exception

interface BaseListener {

    interface onReadItemsFinished {
        fun onReadFinished(list: ArrayList<Any>)
    }


    interface onDeleteFinished {
        fun onDeleteFailed(message : String)
        fun onDeleteSuccess ()
    }


    interface  onGetFinished {
        fun onGetFinished(obj : Any)
    }

    interface onLoginFinished {
        fun onLoginSuccess()
        fun onLoginFailed (exception : Exception)
    }

    interface onAddItemFinished {
        fun onAddSuccess ()
        fun onAddFailed (message : String)
    }
}