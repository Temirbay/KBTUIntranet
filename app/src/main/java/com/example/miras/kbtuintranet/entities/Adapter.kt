package com.example.miras.kbtuintranet.entities

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.miras.kbtuintranet.App.Companion.currentTeacher
import com.example.miras.kbtuintranet.App.Companion.role
import com.example.miras.kbtuintranet.mark.my_list.MyMarksContract
import com.example.miras.kbtuintranet.R
import kotlinx.android.synthetic.main.course_item.view.*
import kotlinx.android.synthetic.main.mark_item.view.*
import kotlinx.android.synthetic.main.student_item.view.*
import kotlinx.android.synthetic.main.teacher_item.view.*

class Adapter(private val items : ArrayList<Any>,
              private val context: Context,
              private val view : Any)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    var courseListener : CustomCourseClickListener? = null
    var itemListener : CustomItemClickListener? = null

    object HolderTypes {
        const val STUDENT = 0
        const val TEACHER = 1
        const val COURSE = 2
        const val MARK = 3
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            items[position] is Student -> HolderTypes.STUDENT
            items[position] is Teacher -> HolderTypes.TEACHER
            items[position] is Course -> HolderTypes.COURSE
            else -> HolderTypes.MARK
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HolderTypes.STUDENT -> StudentListViewHolder(LayoutInflater.from(context)
                    .inflate(R.layout.student_item, parent, false))
            HolderTypes.TEACHER -> TeacherListViewHolder(LayoutInflater.from(context)
                    .inflate(R.layout.teacher_item, parent, false))
            HolderTypes.COURSE -> CourseListViewHolder(LayoutInflater.from(context)
                    .inflate(R.layout.course_item, parent, false))
            else -> MarkListViewHolder(LayoutInflater.from(context)
                    .inflate(R.layout.mark_item, parent, false))
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
       if (getItemViewType(position) == HolderTypes.STUDENT) {
           val student = items[position] as Student
           holder.itemView?.tvNameStudent?.text = student.name
           holder.itemView?.tvSurnameStudent?.text = student.surname
           holder.itemView?.tvYearStudent?.text = student.year + " year"
           holder.itemView?.setOnClickListener {
               itemListener?.onItemClick(it, student)
           }
           if (student.imageUrl != "")
               Glide.with(context).load(student.imageUrl).into(holder.itemView.ivPersonStudent)
       }

        else if (getItemViewType(position) == HolderTypes.TEACHER) {
           val teacher = items[position] as Teacher
           holder.itemView?.tvNameTeacher?.text = teacher.name
           holder.itemView?.tvSurnameTeacher?.text = teacher.surname
           holder.itemView?.setOnClickListener {
              itemListener?.onItemClick(it, teacher)
           }
           if (teacher.imageUrl != "")
               Glide.with(context).load(teacher.imageUrl).into(holder.itemView.ivPersonTeacher)
        }

        else if (getItemViewType(position) == HolderTypes.COURSE){
           val course = items[position] as Course
           holder.itemView.tvNameCourse.text = course.name
           holder.itemView.tvFallCourse.text = course.fall
           holder.itemView.tvDescription.text = course.description
           holder.itemView.tvCredits.text = course.credit + " credits"

           if (role == "students") {
               holder.itemView.setOnClickListener {
                   courseListener?.onItemAddCourse(it, course)
               }
           } else {
               if (course.teacherId == currentTeacher.uuid) {
                   holder.itemView.setOnClickListener {
                       courseListener?.onItemMarkStudents(it, course)
                   }
               }
               else {
                   holder.itemView.setOnClickListener {
                       courseListener?.onItemShowStudents(it, course)
                   }
               }
           }
        }

        else {
           val mark = items[position] as Mark
           if (view is MyMarksContract.View) {
               holder.itemView?.tvNameMark?.text = mark.courseName
           }
           else {
               holder.itemView?.tvNameMark?.text = mark.studentName + " " + mark.studentSurname
               holder.itemView.setOnClickListener {
                   itemListener?.onItemClick(it, mark)
               }
           }
           holder.itemView?.tvMarkValueNum?.text = mark.value
           holder.itemView.tvMarkValue.text = getMarkChar(mark.value.toInt())
           holder.itemView.tvMark.text = getMarkString(mark.value.toInt())
           if (getMarkString(mark.value.toInt()) == "Failure")
                holder.itemView.setBackgroundColor(Color.RED)
           if (getMarkString(mark.value.toInt()) == "Great")
                holder.itemView.setBackgroundColor(Color.GREEN)


       }
    }

    private fun getMarkString(value: Int): String {
        if (value >= 90) return "Great"
        if (value >= 75) return "Good"
        if (value >= 50) return "Satisfactory"
        return "Failure"
    }

    private fun getMarkChar(value: Int): String {
        val array = arrayOf("A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D+", "D")
        val intArray = arrayOf(95, 90, 85, 80, 75, 70, 65, 60, 55, 50)
        var cnt = 0
        for (num in intArray) {
            if (value >= num)
                return array[cnt]
            cnt += 1
        }
        return "F"
    }

    class StudentListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
        class TeacherListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
        class CourseListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
        class MarkListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}

interface CustomItemClickListener {
    fun onItemClick(view : View, obj : Any)
}

interface CustomCourseClickListener {
    fun onItemAddCourse (view : View, course : Course)
    fun onItemShowStudents (view : View, course: Course)
    fun onItemMarkStudents (view : View, course : Course)
}