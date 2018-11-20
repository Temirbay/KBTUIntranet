package com.example.miras.kbtuintranet.item

import com.example.miras.kbtuintranet.core.util.BaseListener
import com.example.miras.kbtuintranet.entities.Teacher

interface TeacherRepository {

    fun onUpdateTeacher (teacher : Teacher)
    fun readTeachers (listener : BaseListener.onReadItemsFinished)
    fun insertTeacher (obj : Teacher)
    fun onReadTeacher (id : String, listener : BaseListener.onGetFinished)
    fun onDeleteTeacher (id : String, listener : BaseListener.onDeleteFinished)
    fun readTeachersByUserId(listener: BaseListener.onGetFinished)

}