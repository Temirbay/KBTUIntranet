package com.example.miras.kbtuintranet.mark

import com.example.miras.kbtuintranet.core.util.BaseListener
import com.example.miras.kbtuintranet.App
import com.example.miras.kbtuintranet.entities.Mark
import com.example.miras.kbtuintranet.mark.MarkRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class MarkRepositoryImpl : MarkRepository {

    override fun readMarksByStudent(uuid: String, listener: BaseListener.onReadItemsFinished) {
        val marks = ArrayList<Any>()
        val itemListener : ValueEventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                val items = p0.children.iterator()
                if (!items.hasNext()) listener.onReadFinished(marks)
                while (items.hasNext()) {
                    val index = items.next()
                    val markId = index.key
                    val markItemListener: ValueEventListener = object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {}
                        override fun onDataChange(p0: DataSnapshot) {
                            val map = p0.value as HashMap<String, String>
                            val mark = Mark()
                            mark.studentName = map["studentName"]!!
                            mark.studentSurname = map["studentSurname"]!!
                            mark.studentId = map["studentId"]!!
                            mark.courseName = map["courseName"]!!
                            mark.courseCredit = map["courseCredits"]!!
                            mark.courseId = map["courseId"]!!
                            mark.value = map["value"]!!
                            mark.uuid = map["uuid"]!!
                            marks.add(mark)
                            listener.onReadFinished(marks)
                        }
                    }
                    App.mDb.child("marks").child(markId).addListenerForSingleValueEvent(markItemListener)
                }
            }
        }

        App.mDb.child("students-marks").child(uuid).addListenerForSingleValueEvent(itemListener)
    }

    override fun readMarksByCourse(id: String, listener: BaseListener.onReadItemsFinished) {
        var marks : ArrayList<Any> = ArrayList()
        val itemListener : ValueEventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                val items = p0.children.iterator()
                if (!items.hasNext()) listener.onReadFinished(marks)
                while (items.hasNext()) {
                    val index = items.next()
                    val markId = index.key
                    val studentItemListener: ValueEventListener = object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {}
                        override fun onDataChange(p0: DataSnapshot) {
                            val map = p0.value as HashMap<String, String>
                            val mark = Mark()
                            mark.value = map["value"]!!
                            mark.studentId = map["studentId"]!!
                            mark.courseId = map["courseId"]!!
                            mark.uuid = map["uuid"]!!
                            val listener2: ValueEventListener = object : ValueEventListener {
                                override fun onCancelled(p0: DatabaseError?) {}
                                override fun onDataChange(p0: DataSnapshot?) {
                                    val map2 = p0?.value as HashMap<String, String>
                                    mark.studentName = map2["name"]!!
                                    mark.studentSurname = map2["surname"]!!
                                    val listener2: ValueEventListener = object : ValueEventListener {
                                        override fun onCancelled(p0: DatabaseError?) {}
                                        override fun onDataChange(p0: DataSnapshot?) {
                                            val map3 = p0?.value as HashMap<String, String>
                                            mark.courseName = map3["name"]!!
                                            marks.add(mark)
                                            listener.onReadFinished(marks)
                                        }
                                    }
                                    App.mDb.child("courses").child(mark.courseId).addListenerForSingleValueEvent(listener2)
                                }
                            }
                            App.mDb.child("students").child(mark.studentId).addListenerForSingleValueEvent(listener2)
                        }
                    }
                    App.mDb.child("marks").child(markId).addListenerForSingleValueEvent(studentItemListener)
                }
            }
        }
        App.mDb.child("courses-marks").child(id).orderByKey().addListenerForSingleValueEvent(itemListener)
    }

}