package com.example.miras.kbtuintranet.item.list

import com.example.miras.kbtuintranet.core.util.BaseListener
import com.example.miras.kbtuintranet.entities.Student
import com.example.miras.kbtuintranet.item.StudentRepository
import com.example.miras.kbtuintranet.item.TeacherRepository

class DetailsPresenter(private val studentRepo : StudentRepository,
                       private val teacherRepo : TeacherRepository,
                       override var view : DetailsContract.View?)
    : DetailsContract.Presenter, BaseListener.onReadItemsFinished{

    override fun start() {

    }


    override fun onReadFinished(list: ArrayList<Any>) {
        view?.hideDefaultImage()
        if (list.isEmpty()) view?.showDefaultImage()
        view?.setAdapter(list)
        view?.hideProgress()
    }

    override fun readStudents () {
        view?.showProgress()
        studentRepo.readStudents (this)
    }

    override fun readTeachers () {
        view?.showProgress()
        teacherRepo.readTeachers (this)
    }

    override fun readStudentByCourse (id : String) {
        view?.showProgress()
        studentRepo.readStudentsByCourse (id, this)
    }

    override fun onItemClick(obj: Any) {
        view?.goToStudentDetails((obj as Student).uuid)
    }

}