package com.example.miras.kbtuintranet.profile.update

import com.example.miras.kbtuintranet.entities.Student
import com.example.miras.kbtuintranet.entities.Teacher
import com.example.miras.kbtuintranet.item.StudentRepository
import com.example.miras.kbtuintranet.item.TeacherRepository
import com.example.miras.kbtuintranet.profile.update.UpdateItemContract

class UpdateItemPresenter(private val studentRepo : StudentRepository,
                          private val teacherRepo : TeacherRepository,
                          override var view : UpdateItemContract.View?)
    : UpdateItemContract.Presenter{

    override fun start() {

    }

    override fun updateItem (obj : Any) {
        if (obj is Student)
        studentRepo.onUpdateStudent(obj)
        if (obj is Teacher)
            teacherRepo.onUpdateTeacher(obj)
        view?.onSuccess()
    }


}