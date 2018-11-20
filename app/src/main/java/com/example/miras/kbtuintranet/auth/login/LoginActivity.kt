package com.example.miras.kbtuintranet.auth.login

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Toast
import com.example.miras.kbtuintranet.App.Companion.mAuth
import com.example.miras.kbtuintranet.R
import com.example.miras.kbtuintranet.MainActivity
import com.example.miras.kbtuintranet.auth.register.SignupActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class LoginActivity : AppCompatActivity(), LoginContract.View {

    override val presenter: LoginContract.Presenter by inject { parametersOf(this) }

    var clicked : Int ?= 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        title = "Login"
        if (mAuth.currentUser != null)  onSuccess()
        bnSeePassword.bringToFront()
    }


    fun onClickLogin(view : View) {
        presenter.login(etUsername.text.toString(), etPassword.text.toString())
    }

    fun onClickRegister (view : View) {
        val intent = Intent(this, SignupActivity::class.java);
        startActivity(intent)
    }

    fun onSeePassword (view : View) {
        clicked = (clicked!! + 1) % 2
        if (clicked == 0)
            etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
        else
            etPassword.transformationMethod = PasswordTransformationMethod.getInstance()

        etPassword.setSelection(etPassword.text.toString().length)
    }


    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun onSuccess() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("username", mAuth.currentUser?.email)
        startActivity(intent)
    }

    override fun showMessage(message : String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun goToMain() {
        val intent = Intent (this, MainActivity::class.java)
        startActivity(intent)
    }

}
