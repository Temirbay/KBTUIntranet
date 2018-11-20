package com.example.miras.kbtuintranet.course

import com.example.miras.kbtuintranet.core.util.BaseListener
import com.example.miras.kbtuintranet.App
import com.example.miras.kbtuintranet.entities.Course
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class CourseRepositoryImpl  : CourseRepository {

    override fun readCoursesByTeacher(id: String, listener: BaseListener.onReadItemsFinished) {
        var courses : ArrayList<Any> = ArrayList()
        val itemListener : ValueEventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                val items = p0.children.iterator()
                if (!items.hasNext()) listener.onReadFinished(courses)
                while (items.hasNext()) {
                    val index = items.next()
                    val courseId = index.key
                    val studentItemListener: ValueEventListener = object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {}
                        override fun onDataChange(p0: DataSnapshot) {
                            val map = p0.value as HashMap<String, String>
                            val course = Course(map["name"]!!, map["fall"]!!,
                                    map["credit"]!!, map["description"]!!, map["teacherId"]!!)
                            course.uuid = p0.key!!
                            courses.add(course)
                            listener.onReadFinished(courses)
                        }
                    }
                    App.mDb.child("courses").child(courseId).addListenerForSingleValueEvent(studentItemListener)
                }
            }
        }
        App.mDb.child("teachers-courses").child(id).orderByKey().addListenerForSingleValueEvent(itemListener)
    }

    override fun readCoursesByStudent(id: String, listener: BaseListener.onReadItemsFinished) {
        var courses : ArrayList<Any> = ArrayList()
        val itemListener : ValueEventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                val items = p0.children.iterator()
                if (!items.hasNext()) listener.onReadFinished(courses)
                while (items.hasNext()) {
                    val index = items.next()
                    val courseId = index.key
                    val studentItemListener: ValueEventListener = object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {}
                        override fun onDataChange(p0: DataSnapshot) {
                            val map = p0.value as HashMap<String, String>
                            val course = Course(map["name"]!!, map["fall"]!!,
                                    map["credit"]!!, map["description"]!!, map["teacherId"]!!)
                            course.uuid = p0.key!!
                            courses.add(course)
                            listener.onReadFinished(courses)
                        }
                    }
                    App.mDb.child("courses").child(courseId).addListenerForSingleValueEvent(studentItemListener)
                }
            }
        }
        App.mDb.child("students-courses").child(id).orderByKey().addListenerForSingleValueEvent(itemListener)
    }



    override fun readCourses(listener: BaseListener.onReadItemsFinished) {
        var courses : ArrayList<Any> = ArrayList()
        val itemListener : ValueEventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                val items = p0.children.iterator()
                while (items.hasNext()) {
                    val index = items.next()
                    val map = index.value as HashMap<String, String>
                    val course = Course(map["name"]!!, map["fall"]!!,
                            map["credit"]!!, map["description"]!!, map["teacherId"]!!)
                    course.uuid = index.key!!
                    courses.add(course)
                }
                listener.onReadFinished(courses)
            }
        }
        App.mDb.child("courses").orderByKey().addListenerForSingleValueEvent(itemListener)
    }


    override fun insertItem(course: Course) {
        val ref = App.mDb.child("courses").push()
        course.uuid = ref.key.toString()
        ref.setValue(course)

        val ref2 = App.mDb.child("teachers-courses")
                .child(App.currentTeacher.uuid)
                .child(course.uuid)
        ref2.setValue(true)
    }


    override fun addCourse(course: Course, listener: BaseListener.onAddItemFinished) {
        val itemListener: ValueEventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.hasChild(App.currentStudent.uuid)) {
                    listener.onAddFailed("Вы уже выбрали этот курс")
                    return
                } else {
                    val ref = App.mDb.child("marks").push()
                    ref.child("studentName").setValue(App.currentStudent.name)
                    ref.child("studentSurname").setValue(App.currentStudent.surname)
                    ref.child("studentId").setValue(App.currentStudent.uuid)
                    ref.child("courseId").setValue(course.uuid)
                    ref.child("courseName").setValue(course.name)
                    ref.child("courseCredits").setValue(course.credit)
                    ref.child("uuid").setValue(ref.key)
                    ref.child("value").setValue("0")

                    App.mDb.child("courses-students")
                            .child(course.uuid)
                            .child(App.currentStudent.uuid)
                            .setValue(true)

                    App.mDb.child("courses-marks")
                            .child(course.uuid)
                            .child(ref.key)
                            .setValue(true)

                    App.mDb.child("students-courses")
                            .child(App.currentStudent.uuid)
                            .child(course.uuid)
                            .setValue(true)

                    App.mDb.child("students-marks")
                            .child(App.currentStudent.uuid)
                            .child(ref.key)
                            .setValue(true)

                    listener.onAddSuccess()
                }
            }
        }
        App.mDb.child("courses-students").child(course.uuid).addListenerForSingleValueEvent(itemListener)
    }
}