package com.example.miras.kbtuintranet.course.details

import com.example.miras.kbtuintranet.core.util.BaseListener
import com.example.miras.kbtuintranet.entities.Student
import com.example.miras.kbtuintranet.item.StudentRepository

class CourseDetailPresenter(private val studentRepo : StudentRepository,
                            override var view : CourseDetailsContract.View?)
    : CourseDetailsContract.Presenter, BaseListener.onReadItemsFinished{

    override fun start() {

    }

    override fun onReadFinished(list: ArrayList<Any>) {
        view?.hideDefaultImage()
        if (list.isEmpty()) view?.showDefaultImage()
        view?.setAdapter(list)
        view?.hideProgress()
    }


    override fun readStudentByCourse (id : String) {
        view?.showProgress()
        studentRepo.readStudentsByCourse (id, this)
    }

    override fun onItemClick(obj: Any) {
        view?.goToStudentDetails((obj as Student).uuid)
    }

}