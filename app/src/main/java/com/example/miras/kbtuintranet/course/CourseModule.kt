package com.example.miras.kbtuintranet.course

import com.example.miras.kbtuintranet.course.add.AddCourseContract
import com.example.miras.kbtuintranet.course.add.AddCoursePresenter
import com.example.miras.kbtuintranet.course.details.CourseDetailPresenter
import com.example.miras.kbtuintranet.course.details.CourseDetailsContract
import com.example.miras.kbtuintranet.course.list.CoursesListContract
import com.example.miras.kbtuintranet.course.list.CoursesListPresenter
import com.example.miras.kbtuintranet.course.my_list.MyCoursesContract
import com.example.miras.kbtuintranet.course.my_list.MyCoursesPresenter
import org.koin.dsl.module.module

val courseModule = module {

    factory { (view : CourseDetailsContract.View) -> CourseDetailPresenter(get(), view)
            as CourseDetailsContract.Presenter}

    factory { (view : CoursesListContract.View) -> CoursesListPresenter(get(), view)
            as CoursesListContract.Presenter}

    factory { (view : AddCourseContract.View) -> AddCoursePresenter(get(), view)
            as AddCourseContract.Presenter}

    factory { (view : MyCoursesContract.View) -> MyCoursesPresenter(get(), view)
            as MyCoursesContract.Presenter}

    single { CourseRepositoryImpl() as CourseRepository }
}