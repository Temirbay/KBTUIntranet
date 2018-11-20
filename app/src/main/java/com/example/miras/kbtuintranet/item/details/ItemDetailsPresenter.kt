package com.example.miras.kbtuintranet.item.details

import android.net.Uri
import com.example.miras.kbtuintranet.core.util.BaseListener
import com.example.miras.kbtuintranet.App
import com.example.miras.kbtuintranet.entities.Student
import com.example.miras.kbtuintranet.entities.Teacher
import com.example.miras.kbtuintranet.item.StudentRepository
import com.example.miras.kbtuintranet.item.TeacherRepository

class ItemDetailsPresenter (private val teacherRepo : TeacherRepository,
                            private val studetRepo : StudentRepository,
                            override var view : ItemDetailsContract.View?)
    : ItemDetailsContract.Presenter, BaseListener.onGetFinished{

    override fun start() {

    }

    override fun onGetFinished(obj : Any) {
        if (obj is Student)
            view?.setObject(obj)
        else if (obj is Teacher)
            view?.setObject (obj)
        view?.showFragment()
        view?.hideProgress()
    }

    override fun readStudent (id : String) {
        view?.showProgress()
        studetRepo.onReadStudent( id, this)
    }

   override fun readTeacher (id : String) {
        view?.showProgress()
        teacherRepo.onReadTeacher( id, this)
    }

    private fun updateStudent (obj : Any) {
        studetRepo.onUpdateStudent(obj as Student)
    }
    private fun updateTeacher (obj : Any) {
        teacherRepo.onUpdateTeacher(obj as Teacher)
    }

    override fun uploadFile(path : Uri) {
        view?.showProgress()
        App.mStorage.child("mypic.jpg")
                .putFile(path)
                .addOnSuccessListener {
                    val url = it.downloadUrl
                    view?.setImageProfile(url.toString())
                    view?.hideProgress()
                    val obj = view?.getObject()
                    if (obj is Student) {
                        obj.imageUrl = url.toString()
                        updateStudent(obj)
                    }
                    if (obj is Teacher) {
                        obj.imageUrl = url.toString()
                        updateTeacher(obj)
                    }

                }.addOnFailureListener {
                    view?.showMessage(it.message.toString())
                }
    }
}