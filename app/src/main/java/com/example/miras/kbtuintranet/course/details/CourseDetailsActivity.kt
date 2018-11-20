package com.example.miras.kbtuintranet.course.details

import android.content.Intent
import android.graphics.drawable.ClipDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.example.miras.kbtuintranet.entities.Adapter
import com.example.miras.kbtuintranet.entities.CustomItemClickListener
import com.example.miras.kbtuintranet.R
import com.example.miras.kbtuintranet.item.details.StudentDetailsActivity
import kotlinx.android.synthetic.main.activity_course_details.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class CourseDetailsActivity : AppCompatActivity(), CourseDetailsContract.View
        , CustomItemClickListener {

    override val presenter: CourseDetailsContract.Presenter by inject { parametersOf(this) }
    lateinit var curId : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_details)
        readIntent ()
        title = "Students"
        recyclerView.layoutManager = LinearLayoutManager(this)
        val itemDecor = DividerItemDecoration(this, ClipDrawable.HORIZONTAL)
        recyclerView.addItemDecoration(itemDecor)
    }

    private fun readIntent() {
        val intent = intent
        if (intent.hasExtra("id")) {
            curId = intent.getStringExtra("id")
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.readStudentByCourse(curId)
    }

    override fun onItemClick(view: View, obj: Any) {
        presenter.onItemClick (obj)
    }

    override fun goToStudentDetails(uuid : String) {
        val intent = Intent(this, StudentDetailsActivity::class.java)
        intent.putExtra("id", uuid)
        startActivity(intent)
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    override fun hideDefaultImage() {
        ivDefault.visibility = View.GONE
    }

    override fun showDefaultImage() {
        ivDefault.visibility = View.VISIBLE
    }
    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun setAdapter(items: ArrayList<Any>) {
        val adapter = Adapter(items, this, this)
        adapter.itemListener = this
        recyclerView.adapter = adapter
    }
}
