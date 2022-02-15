package com.rover.roverandroiddemo.database.dog

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DogDao {
    @Query("SELECT * FROM dog ORDER BY name")
    fun getListOfAllDogs(): Flow<List<Dog>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(dog: Dog): Long
}