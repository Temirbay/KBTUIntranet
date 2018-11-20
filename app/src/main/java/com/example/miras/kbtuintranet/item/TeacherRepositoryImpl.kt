package com.example.miras.kbtuintranet.item

import com.example.miras.kbtuintranet.core.util.BaseListener
import com.example.miras.kbtuintranet.App
import com.example.miras.kbtuintranet.entities.Teacher
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class TeacherRepositoryImpl : TeacherRepository {

    override fun readTeachersByUserId(listener: BaseListener.onGetFinished) {
        val id = App.mAuth.currentUser?.uid
        val itemListener2 : ValueEventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                val items = p0.children.iterator()
                while (items.hasNext()) {
                    val index = items.next()
                    val map = index.value as HashMap<String, String>
                    val teacher = Teacher(map["name"]!!, map["surname"]!!,
                            map["uuid"]!!, map["imageUrl"]!!)
                    teacher.uuid = index!!.key
                    App.currentTeacher = teacher
                    if (map["userId"].equals(id)) {
                        listener.onGetFinished(teacher)
                        return
                    }
                }
            }
        }
        App.mDb.child("teachers").orderByKey().addListenerForSingleValueEvent(itemListener2)
    }

    override fun readTeachers(listener: BaseListener.onReadItemsFinished) {
        var teachers : ArrayList<Any> = ArrayList()
        val itemListener : ValueEventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                val items = p0.children.iterator()
                while (items.hasNext()) {
                    val index = items.next()
                    val map = index.value as HashMap<String, String>
                    val teacher = Teacher(map["name"]!!, map["surname"]!!)
                    teacher.uuid = index.key!!
                    teacher.imageUrl = map["imageUrl"]!!
                    teachers.add(teacher)
                }
                listener.onReadFinished(teachers)
            }
        }
        App.mDb.child("teachers").orderByKey().addListenerForSingleValueEvent(itemListener)
    }


    override fun onReadTeacher(id : String, listener: BaseListener.onGetFinished) {
        val itemListener: ValueEventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                val map = p0.value as HashMap<String, String>
                val teacher = Teacher(map["name"]!!, map["surname"]!!)
                teacher.uuid = p0.key!!
                teacher.imageUrl = map["imageUrl"]!!
                listener.onGetFinished(teacher)
                return
            }
        }
        App.mDb.child("teachers").child(id).addListenerForSingleValueEvent(itemListener)
    }


    override fun onUpdateTeacher(teacher: Teacher) {
        App.mDb.child("teachers").child(teacher.uuid).child("name").setValue(teacher.name)
        App.mDb.child("teachers").child(teacher.uuid).child("surname").setValue(teacher.surname)
        App.mDb.child("teachers").child(teacher.uuid).child("imageUrl").setValue(teacher.imageUrl)
    }

    override fun insertTeacher(obj: Teacher) {
        val ref = App.mDb.child("teachers").push()
        obj.uuid = ref.key.toString()
        ref.setValue(obj)
    }


    override fun onDeleteTeacher(id: String, listener : BaseListener.onDeleteFinished) {
        val listener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {}
            override fun onDataChange(p0: DataSnapshot?) {
                val map = p0?.value as HashMap<String, String>
                if (map["userId"].equals(App.mAuth.currentUser?.uid)) {
                    App.mDb.child("teachers").child(id).setValue(null)
                    App.mAuth.currentUser?.delete()
                    App.mAuth.signOut()
                    listener.onDeleteSuccess()
                } else {
                    listener.onDeleteFailed("Ошибка доступа")
                    return
                }
            }
        }
        App.mDb.child("teachers").child(id).addListenerForSingleValueEvent(listener)
    }

}