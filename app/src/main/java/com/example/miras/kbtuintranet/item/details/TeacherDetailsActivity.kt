package com.example.miras.kbtuintranet.item.details

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.miras.kbtuintranet.course.my_list.MyCoursesFragment
import com.example.miras.kbtuintranet.entities.Teacher
import com.example.miras.kbtuintranet.R
import com.example.miras.kbtuintranet.course.details.CourseDetailsActivity
import com.example.miras.kbtuintranet.MainActivity
import com.example.miras.kbtuintranet.item.my_list.MyStudentsActivity
import kotlinx.android.synthetic.main.activity_teacher_details.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf


class TeacherDetailsActivity : AppCompatActivity(), ItemDetailsContract.View {

    override val presenter: ItemDetailsContract.Presenter by inject { parametersOf(this) }


    private val PICK_IMAGE_CODE = 1234
    private var curId : String ?= ""
    private lateinit var curTeacher : Any

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_details)
        readIntent()
        title = "Teacher"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onResume() {
        super.onResume()
        presenter.readTeacher (curId!!)
    }

    private fun readIntent () {
        val intent = intent
        curId = intent.getStringExtra("id")
    }

    override fun getObject(): Any {
        return curTeacher
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun showMessage (message : String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun setObject(st : Any) {
        val teacher = st as Teacher
        this.curTeacher = teacher
        tvName.text = teacher.name
        tvSurname.text = teacher.surname
        if (teacher.imageUrl != "")
            setImageProfile(teacher.imageUrl)
    }

    override fun showFragment() {
        val fragment = MyCoursesFragment.newInstance(this, "teacher", curId!!)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout, fragment)
        transaction.commit()
    }

    override fun setImageProfile (url : String) {
        Glide.with(this).load(url).into(profilePhoto)
    }

    fun onLoadPhoto (view : View) {
        val intent = Intent().apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
        }
        startActivityForResult(Intent.createChooser(intent, "Select Photo"), PICK_IMAGE_CODE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) return;
        when (requestCode){
            PICK_IMAGE_CODE -> {
                val filePath = data!!.data
                presenter.uploadFile(filePath)
            }
        }
    }

    override fun goToCourseDetails(uuid: String) {
        val intent = Intent (this, CourseDetailsActivity::class.java)
        intent.putExtra("id", uuid)
        startActivity(intent)
    }

    override fun goToMyStudents(uuid: String) {
        val intent = Intent (this, MyStudentsActivity::class.java)
        intent.putExtra("id", uuid)
        startActivity(intent)
    }


    override fun getContext(): Context {
        return this
    }

    override fun onBackPressed() {
        super.onBackPressed()
        goToMain()
    }


    override fun goToMain() {
        val intent = Intent (this, MainActivity::class.java)
        startActivity(intent)
    }
}
