package com.example.miras.kbtuintranet

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.miras.kbtuintranet.item.list.StudentListFragment
import com.example.miras.kbtuintranet.item.list.TeacherListFragment
import com.example.miras.kbtuintranet.item.add.AddItemActivity
import com.example.miras.kbtuintranet.item.details.StudentDetailsActivity
import com.example.miras.kbtuintranet.item.details.TeacherDetailsActivity
import com.example.miras.kbtuintranet.course.list.CoursesListActivity
import com.example.miras.kbtuintranet.auth.login.LoginActivity
import com.example.miras.kbtuintranet.profile.ProfileActivity
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.app_bar_main2.*
import kotlinx.android.synthetic.main.content_main2.*
import kotlinx.android.synthetic.main.nav_header_main2.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity(),
        NavigationView.OnNavigationItemSelectedListener,
        MainContract.View {

    override val presenter: MainContract.Presenter by inject { parametersOf(this) }

    var username : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        setSupportActionBar(toolbar)
        init()
        readFromIntent ()
        val adapter = MyFragmentPagerAdapter(supportFragmentManager)
        val fragment1 = StudentListFragment.newInstance(this)
        val fragment2 = TeacherListFragment.newInstance(this)
        adapter.addFragment(fragment1, "Students")
        adapter.addFragment(fragment2, "Teachers")
        pager.adapter = adapter
        tabs.setupWithViewPager(pager)
    }

    private fun init() {
        title = "KBTU Intranet"
        nav_view.setNavigationItemSelectedListener(this)
        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        if (App.role == "student")
            bnAddItem.visibility = View.INVISIBLE
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> { presenter.logout () }
            R.id.courses -> { goToCourses () }
            R.id.profile -> { goToProfile() }
            R.id.mycourses -> { presenter.goToMyCourses() }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }


    override fun onResume() {
        super.onResume()
        presenter.readObject()
    }

    fun onClickAddStudent (view : View) {
        goToAddItem ("Student")
    }

    fun onClickAddTeacher (view : View) {
        goToAddItem("Teacher")
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onItemClick(view: View, obj: Any) {
        presenter.onItemClick(obj)
    }

    private fun readFromIntent() {
        val intent = intent
        if (intent.hasExtra("username")) {
            username = intent.getStringExtra("username")
        }
    }


    override fun setNameHeader(name: String) {
        tvNameHeader.text = name
    }

    override fun setImageHeader(imageUrl: String) {
        Glide.with(this).load(imageUrl).into(ivPersonHeader)
    }

    override fun setEmailHeader(email: String?) {
        tvEmailHeader.text = email
    }

    override fun goToLogin () {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun goToCourses() {
        val intent = Intent(this, CoursesListActivity::class.java)
        startActivity(intent)
    }

    override fun goToProfile() {
        val intent = Intent(this, ProfileActivity::class.java)
        intent.putExtra("type", App.role)
        if (App.role == "teachers")
            intent.putExtra("id", App.currentTeacher.uuid)
        if (App.role == "students")
            intent.putExtra("id", App.currentStudent.uuid)
        startActivity(intent)
    }

    override fun goToStudentDetails(id : String) {
        val intent = Intent(this, StudentDetailsActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }

    override fun goToTeacherDetails(id : String) {
        val intent = Intent(this, TeacherDetailsActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }

    private fun goToAddItem(type: String) {
        val intent = Intent (this, AddItemActivity::class.java)
        intent.putExtra("type", type)
        startActivity(intent)
    }

    override fun goToMyCoursesForTeacher(uuid : String) {
        val intent = Intent(this, TeacherDetailsActivity::class.java)
        intent.putExtra("id", uuid)
        startActivity(intent)
    }
    override fun goToMyCoursesForStudent(uuid : String) {
        val intent = Intent(this, StudentDetailsActivity::class.java)
        intent.putExtra("id", uuid)
        startActivity(intent)
    }
}
