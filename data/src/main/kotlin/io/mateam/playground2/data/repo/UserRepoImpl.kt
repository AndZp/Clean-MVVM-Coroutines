package io.mateam.playground2.data.repo

import io.mateam.playground2.data.dataSource.local.LocalUserDataSource
import io.mateam.playground2.data.dataSource.local.sharedPref.UserPreferences
import io.mateam.playground2.data.utils.logDebug
import io.mateam.playground2.domain.entity.result.Result
import io.mateam.playground2.domain.entity.user.User
import io.mateam.playground2.domain.repo.UserRepo
import java.io.IOException

class UserRepoImpl(private val userPreferences: UserPreferences, private val local: LocalUserDataSource) : UserRepo {


    override suspend fun getLoggedInUserId(): Result<String> {
        val loggedInUserId = userPreferences.loggedInUserId
        logDebug("getLoggedInUserId: return [$loggedInUserId]")
        return loggedInUserId
            ?.let { userID -> Result.Success(userID) }
            ?: Result.Error(IOException("getLoggedInUserId: logged in userId is null"))
    }

    override suspend fun saveLoggedInUserId(id: String) {
        logDebug("saveLoggedInUserId id [$id]")
        userPreferences.loggedInUserId = id
    }

    override suspend fun getUser(id: String): Result<User> {
        val result = local.getUser(id)
        logDebug("getUser: userId[$id] return result [${result.javaClass.simpleName}]")
        return result
    }

    override suspend fun saveUser(user: User) {
        logDebug("saveUser: user id [$user.id]")
        local.saveUser(user)
    }
}