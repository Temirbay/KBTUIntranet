package com.example.miras.kbtuintranet.item.add

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.miras.kbtuintranet.*
import com.example.miras.kbtuintranet.MainActivity
import kotlinx.android.synthetic.main.activity_add_student.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class AddItemActivity : AppCompatActivity(), AddItemContract.View {

    override val presenter: AddItemContract.Presenter by inject { parametersOf(this) }

    var type : String ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)
        title = "Add"
        readIntent ()
    }

    private fun readIntent() {
        val intent = intent
        val type1 = intent.getStringExtra("type")
        type = if (type1 == "Student") "Student"
        else "Teacher"
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun onClickInsert (view : View) {
        if (type == "Student")
            presenter.insertItem (etName.text.toString(),
                etSurname.text.toString(), etYear.text.toString());

        if (type == "Teacher")
            presenter.insertItem (etNameTeacher.text.toString(),
                    etSurnameTeacher.text.toString());
    }


    override fun changeStudentFormVisibility(status : Int) {
        if (status  == 0)
            studentForm.visibility = View.GONE
        if (status  == 1)
            studentForm.visibility = View.VISIBLE
    }

    override fun changeTeacherFormVisibility(status : Int) {
        if (status  == 0)
            teacherForm.visibility = View.GONE
        if (status  == 1)
            teacherForm.visibility = View.VISIBLE
    }


    override fun showProgress() {
        progressBar.visibility = View.VISIBLE;
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE;
    }

    override fun goToMain() {
        val intent = Intent (this, MainActivity::class.java)
        startActivity(intent)
    }

}
