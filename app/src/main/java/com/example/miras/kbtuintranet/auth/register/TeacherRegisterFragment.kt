package com.example.miras.kbtuintranet.auth.register

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.miras.kbtuintranet.auth.login.LoginContract
import com.example.miras.kbtuintranet.R
import kotlinx.android.synthetic.main.fragment_teacher_register.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf


class TeacherRegisterFragment : Fragment(), View.OnClickListener, RegisterContract.View{


    override val presenter: RegisterContract.Presenter by inject { parametersOf(this) }

    companion object {
        lateinit var mainView : LoginContract.View
        var clicked : Int ?= 0

        fun newInstance (view : LoginContract.View) : Fragment {
            mainView = view
            return TeacherRegisterFragment()
        }
    }

    override fun onResume() {
        super.onResume()
        bnSeePassword.setOnClickListener(this)
        bnRegister.setOnClickListener(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_teacher_register, container, false)
    }

    override fun onClick(p0: View?) = when (p0?.id) {
        R.id.bnSeePassword -> {
            clicked = (clicked!! + 1) % 2
            if (clicked == 0)
                etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            else
                etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            etPassword.setSelection(etPassword.text.toString().length)
        }
        R.id.bnRegister -> {
            presenter.registerTeacher (etUsername.text.toString(), etPassword.text.toString(),
                    etName.text.toString(), etSurname.text.toString())
        }
        else -> { }
    }

    override fun hideProgress() {
        mainView.hideProgress()
    }


    override fun showProgress() {
        mainView.showProgress()
    }

    override fun onSuccess() {
        mainView.goToMain()
    }

    override fun showMessage(message: String) {
        mainView.showMessage(message)
    }

    override fun onInsert() {
        presenter.insertTeacher (etName.text.toString(), etSurname.text.toString())
    }

}