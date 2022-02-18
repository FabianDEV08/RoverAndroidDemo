package com.rover.roverandroiddemo.database.owner

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "owner")
class Owner (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val age: Int,
    val address: String,
    val phone: String
) {
    constructor(name: String, age: Int, address: String, phone: String) :
            this(0, name, age, address, phone)
}

class OwnerAndDog(
    val dogName: String,
    val dogBreed: String,
    val dogAge: Int,
    val dogSex: String,
    val dogPicture: String,
    val ownerName: String,
    val ownerAge: Int,
    val ownerAddress: String,
    val ownerPhone: String
)