package com.example.miras.kbtuintranet.item

import android.util.Log
import com.example.miras.kbtuintranet.core.util.BaseListener
import com.example.miras.kbtuintranet.App
import com.example.miras.kbtuintranet.entities.Student
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class StudentRepositoryImpl : StudentRepository {

    override fun readStudentsByUserId(listener: BaseListener.onGetFinished) {
        val id = App.mAuth.currentUser?.uid
        val itemListener : ValueEventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                val items = p0.children.iterator()
                while (items.hasNext()) {
                    val index = items.next()
                    val map = index.value as HashMap<String, String>
                    val student = Student(map["name"]!!, map["surname"]!!,
                            map["year"]!!, map["uuid"]!!, map["imageUrl"]!!)
                    student.uuid = index!!.key
                    if (map["userId"].equals(id)) {
                        listener.onGetFinished(student)
                        return
                    }
                }
            }
        }
        App.mDb.child("students").orderByKey().addListenerForSingleValueEvent(itemListener)
    }

    override fun insertStudent(obj: Student) {
        val ref = App.mDb.child("students").push()
        obj.uuid = ref.key.toString()
        ref.setValue(obj)
    }


    override fun onDeleteStudent(id: String, listener : BaseListener.onDeleteFinished) {
        val listener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {}
            override fun onDataChange(p0: DataSnapshot?) {
                val map = p0?.value as HashMap<String, String>
                if (map["userId"].equals(App.mAuth.currentUser?.uid)) {
                    App.mDb.child("students").child(id).setValue(null)
                    App.mAuth.currentUser?.delete()
                    App.mAuth.signOut()
                    listener.onDeleteSuccess()
                } else {
                    listener.onDeleteFailed("Ошибка доступа")
                    return
                }
            }
        }
        App.mDb.child("students").child(id).addListenerForSingleValueEvent(listener)
    }


    override fun onReadStudent(id : String, listener: BaseListener.onGetFinished) {
        val itemListener: ValueEventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                val map = p0.value as HashMap<String, String>
                val student = Student(map["name"]!!, map["surname"]!!, map["year"]!!)
                student.uuid = p0.key!!
                student.imageUrl = map["imageUrl"]!!
                listener.onGetFinished(student)
                return

            }
        }
        App.mDb.child("students").child(id).addListenerForSingleValueEvent(itemListener)
    }


    override fun markStudent(id: String, value : String) {
        App.mDb.child("marks").child(id).child("value").setValue(value)
    }


    override fun onUpdateStudent(obj: Student) {
        App.mDb.child("students").child(obj.uuid).child("name").setValue(obj.name)
        App.mDb.child("students").child(obj.uuid).child("surname").setValue(obj.surname)
        App.mDb.child("students").child(obj.uuid).child("year").setValue(obj.year)
        App.mDb.child("students").child(obj.uuid).child("imageUrl").setValue(obj.imageUrl)
    }

    override fun readStudentsByCourse(id : String, listener: BaseListener.onReadItemsFinished) {
        val students : ArrayList<Any> = ArrayList()
        val itemListener : ValueEventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                val items = p0.children.iterator()
                if (!items.hasNext()) listener.onReadFinished(students)
                while (items.hasNext()) {
                    val index = items.next()
                    val studentId = index.key
                    Log.d ("studentId", studentId)
                    val studentItemListener: ValueEventListener = object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {}
                        override fun onDataChange(p0: DataSnapshot) {
                            val map = p0.value as HashMap<String, String>
                            val student = Student(map["name"]!!, map["surname"]!!, map["year"]!!)
                            student.uuid = p0.key!!
                            student.imageUrl = map["imageUrl"]!!
                            students.add(student)
                            listener.onReadFinished(students)
                        }
                    }
                    App.mDb.child("students").child(studentId).addListenerForSingleValueEvent(studentItemListener)
                }
            }
        }
        App.mDb.child("courses-students").child(id).orderByKey().addListenerForSingleValueEvent(itemListener)
    }

    override fun readStudents (listener: BaseListener.onReadItemsFinished) {
        var students : ArrayList<Any> = ArrayList()
        val itemListener : ValueEventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                val items = p0.children.iterator()
                while (items.hasNext()) {
                    val index = items.next()
                    val map = index.value as HashMap<String, String>
                    val student = Student(map["name"]!!, map["surname"]!!, map["year"]!!)
                    student.uuid = index.key!!
                    student.imageUrl = map["imageUrl"]!!
                    students.add(student)
                }
                listener.onReadFinished(students)
            }
        }
        App.mDb.child("students").orderByKey().addListenerForSingleValueEvent(itemListener)
    }
}