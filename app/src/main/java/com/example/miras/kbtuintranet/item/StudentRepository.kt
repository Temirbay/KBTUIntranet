package com.example.miras.kbtuintranet.item

import com.example.miras.kbtuintranet.core.util.BaseListener
import com.example.miras.kbtuintranet.entities.Student

interface StudentRepository {
    fun insertStudent (obj : Student)
    fun readStudentsByCourse(id : String, listener : BaseListener.onReadItemsFinished)
    fun onReadStudent (id : String, listener : BaseListener.onGetFinished)
    fun onDeleteStudent (id : String, listener : BaseListener.onDeleteFinished)
    fun readStudents (listener : BaseListener.onReadItemsFinished)
    fun markStudent(id: String, value: String)
    fun onUpdateStudent (obj : Student)
    fun readStudentsByUserId(listener: BaseListener.onGetFinished)
}