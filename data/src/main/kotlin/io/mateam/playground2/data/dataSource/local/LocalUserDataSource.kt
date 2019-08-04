package io.mateam.playground2.data.dataSource.local

import io.mateam.playground2.data.dataSource.local.dao.UserDao
import io.mateam.playground2.data.dataSource.local.mapper.DbUserMapper
import io.mateam.playground2.data.utils.logDebug
import io.mateam.playground2.domain.entity.result.Result
import io.mateam.playground2.domain.entity.user.User
import java.io.IOException

interface LocalUserDataSource {
    suspend fun getUser(id: String): Result<User>
    suspend fun insertUser(user: User)
    suspend fun updateUser(user: User)
    suspend fun removeUser(user: User)
}

class RoomDbUserDataSource(private val db: UserDao, private val mapper: DbUserMapper) : LocalUserDataSource {

    override suspend fun insertUser(user: User) {
        logDebug("insertUser: user [$user]")
        val userDbModel = mapper.mapToDb(user)
        db.insertUser(userDbModel)
    }

    override suspend fun updateUser(user: User) {
        logDebug("update: user [$user]")
        val userDbModel = mapper.mapToDb(user)
        db.updateUser(userDbModel)
    }

    override suspend fun getUser(id: String): Result<User> {
        logDebug("getUser: user id [$id]")
        val dbResponse = db.getUserById(id)
        return if (dbResponse.isEmpty()) {
            logDebug("getUser: user id [$id] can not be found. Return Result.Error")
            Result.Error(IOException("User with ID [$id] can not be found"))
        } else {
            val user = mapper.mapFromDb(dbResponse.first())
            logDebug("getUser: user id [$id] found in DB. Return Result.Success")
            Result.Success(user)
        }
    }

    override suspend fun removeUser(user: User) {
        logDebug("removeUser: userId [${user.id}]")
        // TBD
    }
}