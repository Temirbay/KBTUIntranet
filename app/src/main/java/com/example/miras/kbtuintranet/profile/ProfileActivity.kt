package com.example.miras.kbtuintranet.profile

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.miras.kbtuintranet.profile.update.UpdateFragment
import com.example.miras.kbtuintranet.entities.Student
import com.example.miras.kbtuintranet.entities.Teacher
import com.example.miras.kbtuintranet.R
import com.example.miras.kbtuintranet.MainActivity
import com.example.miras.kbtuintranet.mark.my_list.MyMarksActivity
import com.example.miras.kbtuintranet.auth.login.LoginActivity
import kotlinx.android.synthetic.main.activity_profile.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class ProfileActivity : AppCompatActivity(), ProfileContract.View {


    override val presenter: ProfileContract.Presenter by inject { parametersOf(this) }

    private val PICK_IMAGE_CODE = 1234

    var curId : String ?= ""
    var curType : String ?= ""
     lateinit var curObject : Any

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        title = "Profile"
        readIntent()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onResume() {
        super.onResume()
        presenter.readItem()
    }

    override fun changeVisibility() {
        if (mainForm.visibility == View.VISIBLE) {
            mainForm.visibility = View.GONE
            frameLayout.visibility = View.VISIBLE
        }
        else {
            mainForm.visibility = View.VISIBLE
            frameLayout.visibility = View.GONE
            presenter.readItem()
        }
    }

    override fun showUpdateFragment () {
        changeVisibility()
        val updateFragment = UpdateFragment.newInstance(this)
        val transaction = supportFragmentManager.beginTransaction()
       transaction.replace(R.id.frameLayout, updateFragment)
        transaction.commit()
    }


    override fun setItems(obj: Any) {
        if (obj is Student) {
            tvName.text = obj.name
            tvSurname.text = obj.surname
            tvYear.text = obj.year + " year"
            if (obj.imageUrl != "")
                setImageProfile(obj.imageUrl)
        }

        if (obj is Teacher) {
            tvName.text = obj.name
            tvSurname.text = obj.surname
            if (obj.imageUrl != "")
                setImageProfile(obj.imageUrl)
        }
    }

    private fun readIntent () {
        val intent = intent
        curType = intent.getStringExtra("type")
        curId = intent.getStringExtra("id")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.profile_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.edit -> { showUpdateFragment() }
            R.id.transcript -> {presenter.goToMyMarks ()}
        }
        return super.onOptionsItemSelected(item)
    }

    override fun goToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
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

    override fun setObject(obj : Any) {
        this.curObject = obj
    }

    override fun getObject(): Any = curObject

    override fun showForm ()  {
        mainForm.visibility = View.VISIBLE
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

    override fun goToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    override fun goToMyMarks() {
        val intent = Intent(this, MyMarksActivity::class.java)
        startActivity(intent)
    }
}


