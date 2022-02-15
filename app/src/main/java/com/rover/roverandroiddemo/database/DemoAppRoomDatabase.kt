package com.rover.roverandroiddemo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rover.roverandroiddemo.database.dog.Dog
import com.rover.roverandroiddemo.database.dog.DogDao
import com.rover.roverandroiddemo.database.owner.Owner
import com.rover.roverandroiddemo.database.owner.OwnerDao

@Database(
    entities = [
        Dog::class,
        Owner::class
    ],
    version = 3,
    exportSchema = false
)
abstract class DemoAppRoomDatabase: RoomDatabase() {
    abstract val dogDao: DogDao
    abstract val ownerDao: OwnerDao
}

private lateinit var INSTANCE: DemoAppRoomDatabase

fun getDatabase(context: Context): DemoAppRoomDatabase {
    // if the INSTANCE is not null, then return it,
    // if it is, then create the database
    synchronized(DemoAppRoomDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                DemoAppRoomDatabase::class.java,
                "demo_database")
                .build()
        }
    }
    return INSTANCE
}