package com.example.miras.kbtuintranet.profile.update

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.miras.kbtuintranet.profile.ProfileContract
import com.example.miras.kbtuintranet.entities.Student
import com.example.miras.kbtuintranet.entities.Teacher
import com.example.miras.kbtuintranet.R
import kotlinx.android.synthetic.main.fragment_update.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class UpdateFragment : Fragment(), UpdateItemContract.View {

    override val presenter: UpdateItemContract.Presenter by inject { parametersOf(this) }

    companion object {
        lateinit var mainView : ProfileContract.View

        fun newInstance (view1 : ProfileContract.View) : Fragment{
            mainView = view1
            return UpdateFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_update, container, false)
    }

    override fun onResume() {
        super.onResume()
        setItems(mainView.getObject())
        bnUpdate.setOnClickListener {
            val obj = mainView.getObject()
            if (obj is Student) {
                obj.name = etName.text.toString()
                obj.surname = etSurname.text.toString()
                obj.year = etYear.text.toString()
            }
            if (obj is Teacher) {
                obj.name = etNameTeacher.text.toString()
                obj.surname = etSurnameTeacher.text.toString()
            }
            presenter.updateItem(obj)
        }
    }


    private fun setItems(obj: Any) {
        if (obj is Student) {
            studentForm.visibility = View.VISIBLE
            etName.text = SpannableStringBuilder(obj.name)
            etName.setSelection(etName.text.length)
            etSurname.text = SpannableStringBuilder(obj.surname)
            etSurname.setSelection(etSurname.text.length)
            etYear.text = SpannableStringBuilder(obj.year)
            etYear.setSelection(etYear.text.length)
        }

        if (obj is Teacher) {
            teacherForm.visibility = View.VISIBLE
            etNameTeacher.text = SpannableStringBuilder(obj.name)
            etNameTeacher.setSelection(etNameTeacher.text.length)
            etSurnameTeacher.text = SpannableStringBuilder(obj.surname)
            etSurnameTeacher.setSelection(etSurnameTeacher.text.length)
        }
    }

    override fun onSuccess() {
        mainView.changeVisibility()
    }

    override fun showMessage(message: String) {
        mainView.showMessage(message)
    }
    override fun hideProgress() {

    }

    override fun showProgress() {

    }


}