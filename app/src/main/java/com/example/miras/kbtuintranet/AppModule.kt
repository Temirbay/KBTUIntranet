package com.example.miras.kbtuintranet

import com.example.miras.kbtuintranet.course.CourseRepository
import com.example.miras.kbtuintranet.course.CourseRepositoryImpl
import com.example.miras.kbtuintranet.mark.MarkRepository
import com.example.miras.kbtuintranet.mark.MarkRepositoryImpl
import com.example.miras.kbtuintranet.item.StudentRepository
import com.example.miras.kbtuintranet.item.StudentRepositoryImpl
import com.example.miras.kbtuintranet.item.TeacherRepository
import com.example.miras.kbtuintranet.item.TeacherRepositoryImpl
import com.example.miras.kbtuintranet.auth.login.LoginRepository
import com.example.miras.kbtuintranet.auth.login.LoginRepositoryImpl
import com.example.miras.kbtuintranet.course.add.AddCourseContract
import com.example.miras.kbtuintranet.course.add.AddCoursePresenter
import com.example.miras.kbtuintranet.course.details.CourseDetailPresenter
import com.example.miras.kbtuintranet.course.details.CourseDetailsContract
import com.example.miras.kbtuintranet.item.list.DetailsContract
import com.example.miras.kbtuintranet.item.list.DetailsPresenter
import com.example.miras.kbtuintranet.item.details.ItemDetailsContract
import com.example.miras.kbtuintranet.item.details.ItemDetailsPresenter
import com.example.miras.kbtuintranet.course.list.CoursesListContract
import com.example.miras.kbtuintranet.course.list.CoursesListPresenter
import com.example.miras.kbtuintranet.auth.login.LoginContract
import com.example.miras.kbtuintranet.auth.login.LoginPresenter
import com.example.miras.kbtuintranet.course.my_list.MyCoursesContract
import com.example.miras.kbtuintranet.course.my_list.MyCoursesPresenter
import com.example.miras.kbtuintranet.mark.my_list.MyMarksContract
import com.example.miras.kbtuintranet.mark.my_list.MyMarksPresenter
import com.example.miras.kbtuintranet.item.my_list.MyStudentsContract
import com.example.miras.kbtuintranet.item.my_list.MyStudentsPresenter
import com.example.miras.kbtuintranet.auth.register.RegisterContract
import com.example.miras.kbtuintranet.auth.register.RegisterPresenter
import com.example.miras.kbtuintranet.profile.ProfileContract
import com.example.miras.kbtuintranet.profile.ProfilePresenter
import com.example.miras.kbtuintranet.profile.update.UpdateItemContract
import com.example.miras.kbtuintranet.profile.update.UpdateItemPresenter
import org.koin.dsl.module.module


val appModule = module{
    factory { (view : MainContract.View) -> MainPresenter(get(), get(), get(), view)
            as MainContract.Presenter}

    factory { (view : ProfileContract.View) -> ProfilePresenter(get(), get(), view)
            as ProfileContract.Presenter}
}