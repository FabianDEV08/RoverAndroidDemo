package com.rover.roverandroiddemo.database.owner

import android.content.Context
import android.database.sqlite.SQLiteException
import android.os.Build
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rover.roverandroiddemo.database.DemoAppRoomDatabase
import com.rover.roverandroiddemo.database.DemoAppRoomDatabaseTest.Companion.populateDatabase
import com.rover.roverandroiddemo.database.dog.DogDao
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import java.util.concurrent.Executors
import kotlin.test.assertEquals

@Config(sdk = [Build.VERSION_CODES.O_MR1])
@RunWith(AndroidJUnit4::class)
class OwnerTest {

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
    fun will_get_all_owners_data_correctly() = runBlocking {
        populateDatabase(database.ownerDao, database.dogDao)

        val owners = database.ownerDao.getAllOwners()
        var currentOwnerData = owners[0]
        assertEquals("Fabian", currentOwnerData.name)
        currentOwnerData = owners[1]
        assertEquals("Daniel", currentOwnerData.name)
        currentOwnerData = owners[2]
        assertEquals("Laura", currentOwnerData.name)
    }

    @Test
    fun will_retrieve_dog_and_owner_data_by_owner_id() = runBlocking {
        populateDatabase(database.ownerDao, database.dogDao)
        val ownerAndDogData = database.ownerDao.getOwnerAndDogByOwnerId(2).first()
        assertEquals("Daniel", ownerAndDogData.ownerName)
        assertEquals(32, ownerAndDogData.ownerAge)
        assertEquals("Crazy Legs", ownerAndDogData.dogName)
        assertEquals("Greyhound", ownerAndDogData.dogBreed)
    }

    @Test
    fun database_will_throw_exception_when_inserting_invalid_data() = runBlocking {
        val owner = Owner(1, "Somebody", 23, "Somewhere", "11111111")
        try {
            database.ownerDao.insert(owner)
            database.ownerDao.insert(owner)
            Assert.fail("Should have thrown SQLiteException")
        } catch (ex: SQLiteException) {
            Assert.assertNotNull(ex)
        }
    }
}