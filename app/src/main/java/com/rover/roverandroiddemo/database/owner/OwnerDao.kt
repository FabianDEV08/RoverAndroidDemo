package com.rover.roverandroiddemo.database.owner

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface OwnerDao {
    @Query("SELECT * from owner ORDER BY name ASC")
    suspend fun getAllOwnersAlphabetically(): List<Owner>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(owner: Owner): Long
}