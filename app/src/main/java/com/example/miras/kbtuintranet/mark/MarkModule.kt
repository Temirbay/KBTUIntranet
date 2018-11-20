package com.example.miras.kbtuintranet.mark

import com.example.miras.kbtuintranet.mark.my_list.MyMarksContract
import com.example.miras.kbtuintranet.mark.my_list.MyMarksPresenter
import org.koin.dsl.module.module

val markModule = module {

    factory { (view : MyMarksContract.View) -> MyMarksPresenter(get(), view)
            as MyMarksContract.Presenter}

    single { MarkRepositoryImpl() as MarkRepository }
}