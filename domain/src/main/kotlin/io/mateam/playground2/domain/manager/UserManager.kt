package io.mateam.playground2.domain.manager

import io.mateam.playground2.domain.entity.result.Result
import io.mateam.playground2.domain.entity.user.Anonymous
import io.mateam.playground2.domain.entity.user.User
import io.mateam.playground2.domain.repo.UserRepo
import io.mateam.playground2.domain.utils.logDebug

interface UserManager {
    suspend fun getOrCreateUser(): Result<User>
    suspend fun updateUser(user: User)
}

class UserManagerImpl(private val userRepo: UserRepo) : UserManager {
    override suspend fun getOrCreateUser(): Result<User> {
        logDebug("getOrCreateUser")
        return when (val userIdResult = userRepo.getLoggedInUserId()) {
            is Result.Success -> userRepo.getUser(userIdResult.data)
            is Result.Error -> {
                val newAnonymousUser = createNewAnonymousUser()
                return Result.Success(newAnonymousUser)
            }
        }
    }

    override suspend fun updateUser(user: User) {
        logDebug("insertUser: user [${user.id}]")
        userRepo.updateUser(user)
    }

    private suspend fun createNewAnonymousUser(): Anonymous {
        val newAnonymousUser = User.createAnonymous()
        userRepo.saveLoggedInUserId(newAnonymousUser.id)
        userRepo.insertUser(newAnonymousUser)
        return newAnonymousUser
    }
}