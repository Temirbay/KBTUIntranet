package com.example.miras.kbtuintranet.profile

import android.net.Uri
import com.example.miras.kbtuintranet.core.util.BaseListener
import com.example.miras.kbtuintranet.App.Companion.currentStudent
import com.example.miras.kbtuintranet.App.Companion.currentTeacher
import com.example.miras.kbtuintranet.App.Companion.mStorage
import com.example.miras.kbtuintranet.App.Companion.role
import com.example.miras.kbtuintranet.entities.Student
import com.example.miras.kbtuintranet.entities.Teacher
import com.example.miras.kbtuintranet.item.StudentRepository
import com.example.miras.kbtuintranet.item.TeacherRepository

class  ProfilePresenter(private val studentRepo : StudentRepository,
                        private val teacherRepo : TeacherRepository,
                        override var view : ProfileContract.View?)
    : ProfileContract.Presenter, BaseListener.onDeleteFinished, BaseListener.onGetFinished {

    override fun start() {

    }


    override fun onDeleteFailed(message: String) {
        view?.hideProgress()
        view?.showMessage(message)
    }

    override fun onDeleteSuccess() {
        view?.hideProgress()
        view?.showMessage("Good bye")
        view?.goToLogin()
    }

    override fun deleteItem (id : String) {
        view?.showProgress()
        studentRepo.onDeleteStudent(id, this)
        teacherRepo.onDeleteTeacher(id, this)
    }


    override fun onGetFinished(obj : Any) {
        view?.setObject(obj)
        view?.setItems(obj)
        view?.showForm ()
        view?.hideProgress()
    }

    override fun updateItem (obj : Any) {
        studentRepo.onUpdateStudent(obj as Student)
        teacherRepo.onUpdateTeacher(obj as Teacher)
    }

    override fun uploadFile(path : Uri) {
        view?.showProgress()
        mStorage.child("mypic.jpg")
                .putFile(path)
                .addOnSuccessListener {
                    val url = it.downloadUrl
                    view?.setImageProfile(url.toString())
                    view?.hideProgress()
                    val obj = view?.getObject()
                    if (obj is Student)
                        obj.imageUrl = url.toString()
                    if (obj is Teacher)
                        obj.imageUrl = url.toString()
                    updateItem(obj!!)
                }.addOnFailureListener {
                    view?.showMessage(it.message.toString())
                }
    }

    override fun goToMyMarks() {
        if (role == "teachers") {
            view?.showMessage("Ошибка доступа")
        } else
            view?.goToMyMarks()
    }

    override fun readItem() {
        view?.showProgress()
        if (role == "students")
            studentRepo.onReadStudent(currentStudent.uuid, this)
        else
            teacherRepo.onReadTeacher(currentTeacher.uuid, this)
    }

}