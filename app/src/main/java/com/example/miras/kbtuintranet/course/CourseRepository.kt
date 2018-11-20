package com.example.miras.kbtuintranet.course

import com.example.miras.kbtuintranet.core.util.BaseListener
import com.example.miras.kbtuintranet.entities.Course

interface CourseRepository {

    fun addCourse (course : Course, listener : BaseListener.onAddItemFinished)
    fun readCourses (listener : BaseListener.onReadItemsFinished)

    fun readCoursesByTeacher(id : String, listener : BaseListener.onReadItemsFinished)
    fun readCoursesByStudent (id : String, listener : BaseListener.onReadItemsFinished)

    fun insertItem (course : Course)
}