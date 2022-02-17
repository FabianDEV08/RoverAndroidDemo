package com.rover.roverandroiddemo.database

import com.rover.roverandroiddemo.database.dog.Dog
import com.rover.roverandroiddemo.database.owner.Owner
import kotlinx.coroutines.flow.Flow

class DemoAppRepository(private val database: DemoAppRoomDatabase) {
    suspend fun insertOwner(owner: Owner): Long = database.ownerDao.insert(owner)
    suspend fun insertDog(dog: Dog): Long = database.dogDao.insert(dog)
    suspend fun deleteDogById(id: Int) = database.dogDao.delete(id)
    suspend fun getDogAndOwnerByIds(dogId: Int, ownerId: Int) = database.dogDao.getDogAndOwnerWithIds(dogId, ownerId)
    suspend fun getOwnersAlphabetically(): List<Owner> = database.ownerDao.getAllOwnersAlphabetically()
    val allDogs: Flow<List<Dog>> = database.dogDao.getListOfAllDogs()
}