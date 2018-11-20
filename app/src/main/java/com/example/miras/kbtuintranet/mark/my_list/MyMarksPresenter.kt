package com.example.miras.kbtuintranet.mark.my_list

import com.example.miras.kbtuintranet.core.util.BaseListener
import com.example.miras.kbtuintranet.App.Companion.currentStudent
import com.example.miras.kbtuintranet.entities.Mark
import com.example.miras.kbtuintranet.mark.MarkRepository

class MyMarksPresenter(private val markRepo : MarkRepository,
                       override var view : MyMarksContract.View?)
    : MyMarksContract.Presenter, BaseListener.onReadItemsFinished{

    override fun start() {

    }

    override fun onReadFinished(list: ArrayList<Any>) {
        view?.hideProgress ()
        if (list.isEmpty())
            view?.showDefaultImage()
        else
            view?.hideDefaultImage()

        view?.setGpa (countGpa (list))
        view?.setAdapter (list)
    }

    private fun countGpa(list: ArrayList<Any>): Double {
        var creditSum = 0.0
        var gpa = 0.0
        for (item in list) {
            val mark = item as Mark
            creditSum += mark.courseCredit.toDouble()
            gpa += mark.courseCredit.toDouble() * getGpaFromValue(mark.value.toInt())
        }
        return Math.round(gpa / creditSum * 100.0) / 100.0
    }

    private fun getGpaFromValue(value: Int): Double {
        val array = arrayOf(4.0, 3.67, 3.33, 3.0, 2.67, 2.33, 2.0, 1.67, 1.33, 1.0)
        val intArray = arrayOf(95, 90, 85, 80, 75, 70, 65, 60, 55, 50)
        var cnt = 0
        for (num in intArray) {
            if (value >= num)
                return array[cnt]
            cnt += 1
        }
        return 0.0
    }

    override fun readMarksByStudent() {
        view?.showProgress ()
        markRepo.readMarksByStudent (currentStudent.uuid, this)
    }


}