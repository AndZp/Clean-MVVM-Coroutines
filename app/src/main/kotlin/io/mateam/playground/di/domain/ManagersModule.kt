@file:Suppress("RemoveExplicitTypeArguments")

package io.mateam.playground.di.domain

import io.mateam.playground2.domain.manager.UserManager
import io.mateam.playground2.domain.manager.UserManagerImpl
import io.mateam.playground2.domain.repo.UserRepo
import org.koin.dsl.module

val managersModule = module {
    single<UserManager> { UserManagerImpl(get<UserRepo>()) }
}

