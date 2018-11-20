package com.example.miras.kbtuintranet.course.list

import android.content.Intent
import android.graphics.drawable.ClipDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.example.miras.kbtuintranet.App.Companion.role
import com.example.miras.kbtuintranet.entities.Adapter
import com.example.miras.kbtuintranet.entities.Course
import com.example.miras.kbtuintranet.entities.CustomCourseClickListener
import com.example.miras.kbtuintranet.R
import com.example.miras.kbtuintranet.course.add.AddCourseActivity
import com.example.miras.kbtuintranet.MainActivity
import com.example.miras.kbtuintranet.item.my_list.MyStudentsActivity
import com.example.miras.kbtuintranet.course.details.CourseDetailsActivity
import kotlinx.android.synthetic.main.activity_courses.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import kotlin.collections.ArrayList

class CoursesListActivity : AppCompatActivity(), CoursesListContract.View,
        CustomCourseClickListener, AdapterView.OnItemSelectedListener {


    override val presenter: CoursesListContract.Presenter by inject { parametersOf(this) }

    private fun goToMyStudents(uuid: String) {
        val intent = Intent (this, MyStudentsActivity::class.java)
        intent.putExtra("id", uuid)
        startActivity(intent)
    }

    override fun onItemMarkStudents(view: View, course: Course) {
        goToMyStudents(course.uuid)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_courses)
        setVisibilities ()
        title = "Courses"
        bnAddCourse.attachToRecyclerView(recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val itemDecor = DividerItemDecoration(this, ClipDrawable.HORIZONTAL)
        recyclerView.addItemDecoration(itemDecor)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.courses_menu, menu)

        val item = menu?.findItem(R.id.spinner)
        val spinner : Spinner = MenuItemCompat.getActionView(item) as Spinner
        val adapter = ArrayAdapter.createFromResource(this, R.array.spinner_list_array,
                android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = this

        val item2 = menu?.findItem(R.id.action_search)
        val searchView : SearchView = MenuItemCompat.getActionView(item2) as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                presenter.searchByText(newText!!)
                return true
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        goToMain()
        return super.onOptionsItemSelected(item)
    }


    private fun setVisibilities() {
        if (role == "student")
            bnAddCourse.visibility = View.INVISIBLE
    }

    override fun onResume() {
        super.onResume()
        presenter.readItems ()
    }


    override fun setCoursesAdapter(list: ArrayList<Any>) {
        val adapter = Adapter(list, this, this)
        adapter.courseListener = this
        recyclerView.adapter = adapter
    }

    override fun onItemAddCourse(view: View, course : Course) {
        presenter.addCourse (course)
    }

    override fun onItemShowStudents(view: View, course: Course) {
        presenter.showStudents (course)
    }

    override fun showProgress() {
        progressBar.visibility = View.GONE
    }

    override fun hideProgress() {
        progressBar.visibility = View.INVISIBLE
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

    override fun goToCourseDetails(uuid: String) {
        val intent = Intent (this, CourseDetailsActivity::class.java)
        intent.putExtra("id", uuid)
        startActivity(intent)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        goToMain()
    }

    override fun goToMain() {
        val intent = Intent (this, MainActivity::class.java)
        startActivity(intent)
    }

    fun onClickInsertCourse (view : View) {
        val intent = Intent (this, AddCourseActivity::class.java)
        startActivity(intent)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        presenter.onSpinnerItemClick (p2)
    }

    override fun getStringArray(id: Int) : Array<String>? {
        return resources.getStringArray(id)
    }
}

