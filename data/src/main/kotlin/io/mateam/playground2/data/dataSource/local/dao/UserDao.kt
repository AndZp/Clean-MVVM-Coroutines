package io.mateam.playground2.data.dataSource.local.dao

import androidx.room.*
import io.mateam.playground2.data.dataSource.local.entity.UserDbModel

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserDbModel)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUser(user: UserDbModel)

    @Delete
    suspend fun deleteUser(user: UserDbModel)

    @Query("SELECT * FROM UserDbModel WHERE id == :id")
    suspend fun getUserById(id: String): List<UserDbModel>

    @Query("SELECT * FROM UserDbModel")
    suspend fun getUsers(): List<UserDbModel>
}