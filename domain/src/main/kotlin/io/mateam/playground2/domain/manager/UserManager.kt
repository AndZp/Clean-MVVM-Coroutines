package io.mateam.playground2.domain.manager

import io.mateam.playground2.domain.entity.result.Result
import io.mateam.playground2.domain.entity.user.User
import io.mateam.playground2.domain.repo.UserRepo
import io.mateam.playground2.domain.utils.logDebug

interface UserManager {
    suspend fun getOrCreateUser(): Result<User>
    suspend fun saveUser(user: User)
}

class UserManagerImpl(private val userRepo: UserRepo) : UserManager {
    override suspend fun getOrCreateUser(): Result<User> {
        logDebug("getOrCreateUser")
        return when (val userIdResult = userRepo.getLoggedInUserId()) {
            is Result.Success -> userRepo.getUser(userIdResult.data)
            is Result.Error -> {
                val newAnonymousUser = User.createAnonymous()
                userRepo.saveLoggedInUserId(newAnonymousUser.id)
                userRepo.saveUser(newAnonymousUser)
                return Result.Success(newAnonymousUser)
            }
        }
    }

    override suspend fun saveUser(user: User) {
        logDebug("saveUser: user [${user.id}]")
        userRepo.saveUser(user)
    }
}