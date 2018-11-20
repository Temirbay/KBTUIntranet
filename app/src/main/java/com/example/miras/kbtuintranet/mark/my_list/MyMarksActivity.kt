package com.example.miras.kbtuintranet.mark.my_list

import android.graphics.drawable.ClipDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.example.miras.kbtuintranet.entities.Adapter
import com.example.miras.kbtuintranet.R
import kotlinx.android.synthetic.main.activity_my_marks.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class MyMarksActivity : AppCompatActivity(),
        MyMarksContract.View {

    override val presenter: MyMarksContract.Presenter by inject { parametersOf(this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_marks)
        title = "Marks"
        recyclerView.layoutManager = LinearLayoutManager(this)
        val itemDecor = DividerItemDecoration(this, ClipDrawable.HORIZONTAL)
        recyclerView.addItemDecoration(itemDecor)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onResume() {
        super.onResume()
        presenter.readMarksByStudent ()
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

    override fun setAdapter(list: ArrayList<Any>) {
        val adapter = Adapter(list, this, this)
        recyclerView.adapter = adapter
    }

    override fun setGpa(gpa: Double) {
        tvGpa.text = "Gpa: $gpa"
    }

    override fun hideDefaultImage() {
        ivDefault.visibility = View.GONE
    }
    override fun showDefaultImage() {
        ivDefault.visibility = View.VISIBLE
    }


}
