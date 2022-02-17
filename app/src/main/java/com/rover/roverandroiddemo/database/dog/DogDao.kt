package com.rover.roverandroiddemo.database.dog

import androidx.room.*
import com.rover.roverandroiddemo.database.owner.OwnerAndDog
import kotlinx.coroutines.flow.Flow

@Dao
interface DogDao {
    @Query("SELECT dog.name as dogName, dog.breed as dogBreed, dog.Age as dogAge, " +
            "dog.sex as dogSex, dog.dog_picture as dogPicture, owner.name as ownerName, " +
            "owner.age as ownerAge, owner.address as ownerAddress, owner.phone as ownerPhone " +
            "FROM dog INNER JOIN owner ON dog.owner_id == owner.id WHERE owner.id == :ownerId " +
            "AND dog.id == :dogId")
    suspend fun getDogAndOwnerWithIds(dogId: Int, ownerId: Int): List<OwnerAndDog>

    @Query("SELECT * FROM dog ORDER BY name")
    fun getListOfAllDogs(): Flow<List<Dog>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(dog: Dog): Long

    @Query("DELETE FROM dog WHERE dog.id == :id")
    suspend fun delete(id: Int)
}