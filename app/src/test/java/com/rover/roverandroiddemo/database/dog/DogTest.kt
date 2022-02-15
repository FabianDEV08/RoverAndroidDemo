package com.rover.roverandroiddemo.database.dog

import android.content.Context
import android.os.Build
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rover.roverandroiddemo.database.DemoAppRoomDatabase
import com.rover.roverandroiddemo.database.DemoAppRoomDatabaseTest.Companion.populateDatabase
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
class DogTest {

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
    fun will_retrieve_dog_data_correctly() = runBlocking {
        populateDatabase(database.ownerDao, database.dogDao)

        val dogs = database.dogDao.getListOfAllDogs().first()

        var currentDogData = dogs[0]
        assertEquals("Crazy Legs", currentDogData.name)
        currentDogData = dogs[3]
        assertEquals("Mi Chingon", currentDogData.name)
        assertEquals(null, currentDogData.dogPicture)
        currentDogData = dogs[4]
        assertEquals("French Poodle", currentDogData.breed)
        assertEquals(3, currentDogData.age)
    }
}