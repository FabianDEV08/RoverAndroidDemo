package com.rover.roverandroiddemo.database.owner

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface OwnerDao {
    @Query("SELECT dog.name as dogName, dog.breed as dogBreed, dog.Age as dogAge, " +
            "dog.sex as dogSex, dog.dog_picture as dogPicture, owner.name as ownerName, " +
            "owner.age as ownerAge, owner.address as ownerAddress, owner.phone as ownerPhone " +
            "FROM dog LEFT JOIN owner ON dog.owner_id == owner.id WHERE owner.id == :id")
    suspend fun getOwnerAndDogByOwnerId(id: Int): List<OwnerAndDog>

    @Query("SELECT * from owner ORDER BY id ASC")
    suspend fun getAllOwners(): List<Owner>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(owner: Owner): Long
}