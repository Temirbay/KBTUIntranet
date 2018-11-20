package com.example.miras.kbtuintranet.auth

import com.example.miras.kbtuintranet.auth.login.LoginContract
import com.example.miras.kbtuintranet.auth.login.LoginPresenter
import com.example.miras.kbtuintranet.auth.login.LoginRepository
import com.example.miras.kbtuintranet.auth.login.LoginRepositoryImpl
import com.example.miras.kbtuintranet.auth.register.RegisterContract
import com.example.miras.kbtuintranet.auth.register.RegisterPresenter
import org.koin.dsl.module.module

val authModule = module {

    factory { (view : LoginContract.View) -> LoginPresenter(get(), view) as LoginContract.Presenter}

    factory { (view : RegisterContract.View) -> RegisterPresenter(get(), get(), get(), view)
            as RegisterContract.Presenter}

    single { LoginRepositoryImpl() as LoginRepository }
}