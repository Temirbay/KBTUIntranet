package com.example.miras.kbtuintranet.item.list

import android.graphics.drawable.ClipDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.miras.kbtuintranet.MainContract
import com.example.miras.kbtuintranet.entities.Adapter
import com.example.miras.kbtuintranet.entities.CustomItemClickListener
import com.example.miras.kbtuintranet.R
import kotlinx.android.synthetic.main.fragment_student_list.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf


class StudentListFragment : Fragment(), DetailsContract.View,
        CustomItemClickListener {


    override val presenter: DetailsContract.Presenter by inject { parametersOf(this) }

    companion object {
        lateinit var mainView : MainContract.View

        fun newInstance (view : MainContract.View) : Fragment{
            mainView = view
            return StudentListFragment()
        }
    }

    override fun hideDefaultImage() {
        ivDefault.visibility = View.INVISIBLE
    }

    override fun showDefaultImage() {
        ivDefault.visibility = View.VISIBLE
    }


    override fun onResume() {
        super.onResume()
        presenter.readStudents ()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_student_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        studentRecyclerView.layoutManager = LinearLayoutManager(activity)
        val itemDecor = DividerItemDecoration(activity, ClipDrawable.HORIZONTAL)
        studentRecyclerView.addItemDecoration(itemDecor)
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

    override fun setAdapter(items: ArrayList<Any>) {
        val adapter = Adapter(items, activity!!, this)
        adapter.itemListener = this
        studentRecyclerView.adapter = adapter
    }

    override fun onItemClick(view: View, obj: Any) {
        mainView.onItemClick(view, obj)
    }

    override fun goToStudentDetails(uuid: String) {}
}

