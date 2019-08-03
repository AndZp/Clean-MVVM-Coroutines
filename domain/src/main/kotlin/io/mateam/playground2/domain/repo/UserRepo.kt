package io.mateam.playground2.domain.repo

import io.mateam.playground2.domain.entity.user.User
import io.mateam.playground2.domain.entity.result.Result

interface UserRepo {
    suspend fun getLoggedInUserId(): Result<String>

    suspend fun saveLoggedInUserId(id:String)

    suspend fun getUser(id: String): Result<User>

    suspend fun insertUser(user: User)

    suspend fun updateUser(user: User)
}