package com.example.miras.kbtuintranet.auth.register

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.miras.kbtuintranet.App.Companion.mAuth
import com.example.miras.kbtuintranet.auth.login.LoginContract
import com.example.miras.kbtuintranet.R
import com.example.miras.kbtuintranet.MainActivity
import com.example.miras.kbtuintranet.MyFragmentPagerAdapter
import kotlinx.android.synthetic.main.activity_signup.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class SignupActivity : AppCompatActivity(), LoginContract.View{

    override val presenter: LoginContract.Presenter by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        title = "Signup"
        val adapter = MyFragmentPagerAdapter(supportFragmentManager)
        val fragment1 = StudentRegisterFragment.newInstance(this)
        val fragment2 = TeacherRegisterFragment.newInstance(this)
        adapter.addFragment(fragment1, "Student")
        adapter.addFragment(fragment2, "Teacher")
        pager.adapter = adapter
        tabs.setupWithViewPager(pager)
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun onSuccess() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("username", mAuth.currentUser?.email)
        startActivity(intent)
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    override fun goToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
