package com.example.miras.kbtuintranet

import android.app.Application
import com.example.miras.kbtuintranet.auth.authModule
import com.example.miras.kbtuintranet.core.coreModule
import com.example.miras.kbtuintranet.course.courseModule
import com.example.miras.kbtuintranet.entities.Student
import com.example.miras.kbtuintranet.entities.Teacher
import com.example.miras.kbtuintranet.item.itemModule
import com.example.miras.kbtuintranet.mark.markModule
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import org.koin.android.ext.android.startKoin

class App : Application() {

    companion object {
        lateinit var mDb : DatabaseReference
        lateinit var mAuth : FirebaseAuth
        lateinit var role : String
        lateinit var mStorage : StorageReference
        lateinit var currentStudent : Student
        lateinit var currentTeacher : Teacher
        lateinit var currentId : String
    }

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(
                appModule,
                coreModule,
                authModule,
                courseModule,
                itemModule,
                markModule
        ))

        mAuth = FirebaseAuth.getInstance()
        mDb = FirebaseDatabase.getInstance().reference
        mStorage = FirebaseStorage.getInstance().reference
        role = "teachers"
        currentStudent = Student()
        currentTeacher = Teacher()
        currentId = ""
    }
}