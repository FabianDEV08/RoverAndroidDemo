package com.rover.roverandroiddemo.database

import com.rover.roverandroiddemo.database.dog.Dog
import com.rover.roverandroiddemo.database.owner.Owner
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class DemoAppRepository(private val database: DemoAppRoomDatabase) {

    suspend fun insertOwner(owner: Owner): Long = database.ownerDao.insert(owner)
    suspend fun insertDog(dog: Dog): Long = database.dogDao.insert(dog)
    suspend fun getDogAndOwnerByDogId(dogId: Int) = database.ownerDao.getOwnerAndDogByOwnerId(dogId)
    suspend fun getOwnersAlphabetically(): List<Owner> = database.ownerDao.getAllOwnersAlphabetically()
    val allDogs: Flow<List<Dog>> = database.dogDao.getListOfAllDogs()

}