package com.rover.roverandroiddemo.database.dog

import androidx.room.*
import com.rover.roverandroiddemo.database.owner.Owner

@Entity(tableName = "dog", foreignKeys = [
        androidx.room.ForeignKey(
            entity = Owner::class,
            parentColumns = ["id"],
            childColumns = ["owner_id"],
            onDelete = androidx.room.ForeignKey.CASCADE
        )], indices = [Index("owner_id")])
class Dog(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val breed: String,
    val age: Int,
    val sex: String,
    @ColumnInfo(name = "dog_picture")
    val dogPicturePath: String,
    @ColumnInfo(name = "owner_id") val ownerId: Int
) {
    constructor(name: String, breed: String, age: Int, sex: String, dogPicturePath: String, ownerId: Int):
            this(0, name, breed, age, sex, dogPicturePath, ownerId)
}
