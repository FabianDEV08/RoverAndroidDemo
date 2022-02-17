package com.rover.roverandroiddemo.database

import android.content.Context
import android.os.Build
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rover.roverandroiddemo.database.dog.Dog
import com.rover.roverandroiddemo.database.dog.DogDao
import com.rover.roverandroiddemo.database.owner.Owner
import com.rover.roverandroiddemo.database.owner.OwnerDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import java.util.concurrent.Executors
import kotlin.test.assertEquals

@Config(sdk = [Build.VERSION_CODES.O_MR1])
@RunWith(AndroidJUnit4::class)
class DemoAppRoomDatabaseTest {

    private lateinit var dogDao: DogDao
    private lateinit var ownerDao: OwnerDao
    private lateinit var database: DemoAppRoomDatabase
    private val context = ApplicationProvider.getApplicationContext<Context>()

    @Before
    fun createDb() {
        database = Room.inMemoryDatabaseBuilder(
            context, DemoAppRoomDatabase::class.java)
            .setTransactionExecutor(Executors.newSingleThreadExecutor()).build()
        dogDao = database.dogDao
        ownerDao = database.ownerDao
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun will_insert_data_correctly() = runBlocking {
        populateDatabase(database.ownerDao, database.dogDao)
        val owners = database.ownerDao.getAllOwnersAlphabetically()
        assertEquals(3, owners.size)
        val dogs = database.dogDao.getListOfAllDogs().first()
        assertEquals(5, dogs.size)
    }

    companion object {
        @JvmStatic
        fun populateDatabase(ownerDao: OwnerDao, dogDao: DogDao) = runBlocking {
            var owner = Owner(1, "Fabian", 31, "My House #123", "3333333333")
            ownerDao.insert(owner)
            owner = Owner(2, "Daniel", 32, "Another House #456", "4444444444")
            ownerDao.insert(owner)
            owner = Owner(3, "Laura", 29, "Yet Another House #789", "5555555555")
            ownerDao.insert(owner)
            var dog = Dog(1, "Max", "Doberman", 4, "male", null, 1)
            dogDao.insert(dog)
            dog = Dog(2, "Nahala", "French Poodle", 3, "female", null, 3)
            dogDao.insert(dog)
            dog = Dog(3, "Mi Chingon", "Pitbull", 5, "male", null, 1)
            dogDao.insert(dog)
            dog = Dog(4, "Crazy Legs", "Greyhound", 2, "male", null, 2)
            dogDao.insert(dog)
            dog = Dog(5, "Manchas", "Jack Russell", 7, "male", null, 1)
            dogDao.insert(dog)
        }
    }
}