package com.example.miras.kbtuintranet.mark

import com.example.miras.kbtuintranet.core.util.BaseListener

interface MarkRepository {
    fun readMarksByStudent(uuid: String, listener: BaseListener.onReadItemsFinished)
    fun readMarksByCourse(id: String, listener : BaseListener.onReadItemsFinished)
}