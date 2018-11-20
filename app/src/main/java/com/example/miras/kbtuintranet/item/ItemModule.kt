package com.example.miras.kbtuintranet.item

import com.example.miras.kbtuintranet.item.details.ItemDetailsContract
import com.example.miras.kbtuintranet.item.details.ItemDetailsPresenter
import com.example.miras.kbtuintranet.item.list.DetailsContract
import com.example.miras.kbtuintranet.item.list.DetailsPresenter
import com.example.miras.kbtuintranet.item.my_list.MyStudentsContract
import com.example.miras.kbtuintranet.item.my_list.MyStudentsPresenter
import com.example.miras.kbtuintranet.profile.update.UpdateItemContract
import com.example.miras.kbtuintranet.profile.update.UpdateItemPresenter
import org.koin.dsl.module.module

val itemModule = module {

    factory { (view : ItemDetailsContract.View) -> ItemDetailsPresenter(get(), get(), view)
            as ItemDetailsContract.Presenter}

    factory { (view : DetailsContract.View) -> DetailsPresenter(get(), get(), view)
            as DetailsContract.Presenter}

    factory { (view : UpdateItemContract.View) -> UpdateItemPresenter(get(), get(), view)
            as UpdateItemContract.Presenter}

    factory { (view : MyStudentsContract.View) -> MyStudentsPresenter(get(), get(), view)
            as MyStudentsContract.Presenter}

    factory { StudentRepositoryImpl() as StudentRepository }
    factory { TeacherRepositoryImpl() as TeacherRepository }
}