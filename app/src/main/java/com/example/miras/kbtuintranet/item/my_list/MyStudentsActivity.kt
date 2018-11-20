package com.example.miras.kbtuintranet.item.my_list

import android.content.Intent
import android.graphics.drawable.ClipDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import com.example.miras.kbtuintranet.entities.Adapter
import com.example.miras.kbtuintranet.entities.CustomItemClickListener
import com.example.miras.kbtuintranet.entities.Mark
import com.example.miras.kbtuintranet.R
import com.example.miras.kbtuintranet.item.details.StudentDetailsActivity
import kotlinx.android.synthetic.main.activity_my_students.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class MyStudentsActivity : AppCompatActivity(), MyStudentsContract.View,
        CustomItemClickListener, SeekBar.OnSeekBarChangeListener {

    override val presenter: MyStudentsContract.Presenter by inject { parametersOf(this) }

    lateinit var curId : String
    var tvSeekBarValue : TextView ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_students)
        readIntent ()
        title = "Marks"
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
        presenter.readMarksByCourse(curId)
    }

    override fun onItemClick(view: View, obj: Any) {
        showMarkDialog (obj)
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

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun setAdapter(items: ArrayList<Any>) {
        val adapter = Adapter(items, this, this)
        adapter.itemListener = this
        recyclerView.adapter = adapter
    }


    override fun hideDefaultImage() {
        ivDefault.visibility = View.GONE
    }

    override fun showDefaultImage() {
        ivDefault.visibility = View.VISIBLE
    }


    private fun showMarkDialog(obj : Any) {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_custom, null)
        dialogBuilder.setView(dialogView)
        val seekBar = dialogView.findViewById(R.id.seekBar) as SeekBar
        tvSeekBarValue = dialogView.findViewById(R.id.tvSeekBarValue) as TextView
        seekBar.setOnSeekBarChangeListener(this)
        seekBar.progress = (obj as Mark).value.toInt()
        tvSeekBarValue?.text = obj.value
        dialogBuilder.setTitle("Поставьте оценку")
        dialogBuilder.setPositiveButton("Ok") { _, _ ->
            presenter.markStudent ((obj as Mark).uuid, seekBar.progress.toString())
        }
        val builder = dialogBuilder.create()
        builder.show()
    }


    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
        tvSeekBarValue?.text = p1.toString()
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {

    }

    override fun onStopTrackingTouch(p0: SeekBar?) {

    }
}
