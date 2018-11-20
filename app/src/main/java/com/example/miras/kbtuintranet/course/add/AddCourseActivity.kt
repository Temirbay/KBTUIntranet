package com.example.miras.kbtuintranet.course.add

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.miras.kbtuintranet.R
import com.example.miras.kbtuintranet.course.list.CoursesListActivity
import kotlinx.android.synthetic.main.activity_add_course.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class AddCourseActivity : AppCompatActivity(), AddCourseContract.View {

    override val presenter: AddCourseContract.Presenter by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)
        title = "Add"
    }

    fun onClickInsert (view : View) {
        presenter.insertItem (etName.text.toString(), etFall.text.toString(),
                etCredit.text.toString(), etDescription.text.toString())
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE;
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE;
    }

    override fun goToCourses() {
        val intent = Intent (this, CoursesListActivity::class.java)
        startActivity(intent)
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}