package com.example.miras.kbtuintranet.course.my_list

import android.graphics.drawable.ClipDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.miras.kbtuintranet.item.details.ItemDetailsContract
import com.example.miras.kbtuintranet.R
import com.example.miras.kbtuintranet.entities.*
import kotlinx.android.synthetic.main.fragment_course_list.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf


class MyCoursesFragment : Fragment(), MyCoursesContract.View, CustomCourseClickListener {

    override val presenter: MyCoursesContract.Presenter by inject { parametersOf(this) }

    companion object {
        lateinit var mainView : ItemDetailsContract.View
        lateinit var type : String
        lateinit var curId: String

        fun newInstance (view : ItemDetailsContract.View, type1 : String, id1 : String) : Fragment{
            val studentFragment = MyCoursesFragment()
            mainView = view
            type = type1
            curId = id1
            return studentFragment
        }
    }

    override fun showDefaultImage() {
        ivDefault.visibility = View.VISIBLE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater?.inflate(R.layout.fragment_course_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(mainView.getContext())
        val itemDecor = DividerItemDecoration(activity, ClipDrawable.HORIZONTAL)
        recyclerView.addItemDecoration(itemDecor)
        if (type == "student")
            presenter.readCoursesByStudent ((mainView.getObject() as Student).uuid)
        if (type == "teacher")
            presenter.readCourseByTeacher ((mainView.getObject() as Teacher).uuid)
    }

    override fun showProgress() {
        mainView.showProgress()
    }

    override fun hideProgress() {
        mainView.hideProgress()
    }

    override fun showMessage(message: String) {
        mainView.showMessage(message)
    }

    override fun hideDefaultImage() {
        ivDefault.visibility = View.INVISIBLE
    }
    override fun setCoursesAdapter(courses: ArrayList<Any>) {
        val adapter = Adapter(courses, mainView.getContext(), this)
        adapter.courseListener = this
        recyclerView.adapter = adapter
    }

    override fun onItemMarkStudents(view: View, course: Course) {
        mainView.goToMyStudents (course.uuid)
    }

    override fun onItemAddCourse(view: View, course : Course) {
        presenter.addCourse (course)
    }

    override fun onItemShowStudents(view: View, course: Course) {
        mainView.goToCourseDetails (course.uuid)
    }
}