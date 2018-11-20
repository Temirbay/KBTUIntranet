package com.example.miras.kbtuintranet.item.my_list

import com.example.miras.kbtuintranet.core.util.BaseListener
import com.example.miras.kbtuintranet.entities.Student
import com.example.miras.kbtuintranet.item.StudentRepository
import com.example.miras.kbtuintranet.mark.MarkRepository
import java.lang.Character.isDigit

class MyStudentsPresenter(private val markRepo : MarkRepository,
                          private val studentRepo : StudentRepository,
                          override var view : MyStudentsContract.View?)
    : BaseListener.onReadItemsFinished, MyStudentsContract.Presenter {

    override fun start() {

    }


    override fun onReadFinished(list: ArrayList<Any>) {
        view?.hideDefaultImage()
        if (list.isEmpty()) view?.showDefaultImage()
        view?.setAdapter(list)
        view?.hideProgress()
    }


    fun onItemClick(obj: Any) {
        view?.goToStudentDetails((obj as Student).uuid)
    }

    override fun markStudent(id: String, value : String) {
        if (validate (value)) {
            studentRepo.markStudent (id, value)
        }
        view?.onResume()
    }

    private fun validate(value: String): Boolean {
        if (value.isEmpty()) {
            view?.showMessage("Введите оценку")
            return false
        }

        for (char in value.toCharArray())
            if (!isDigit(char)) {
                view?.showMessage("Оценка должна содержать только цифры")
                return false
            }

        if (0 > value.toInt() || value.toInt() > 100) {
            view?.showMessage("Оценка должна быть от 1 до 100")
            return false
        }

        return true
    }

    override fun readMarksByCourse(id: String) {
        view?.showProgress()
        markRepo.readMarksByCourse (id, this)
    }

}