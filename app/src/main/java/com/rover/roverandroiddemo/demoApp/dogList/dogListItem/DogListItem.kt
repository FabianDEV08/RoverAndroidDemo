package com.rover.roverandroiddemo.demoApp.dogList.dogListItem

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.rover.roverandroiddemo.database.dog.Dog
import com.rover.roverandroiddemo.databinding.ListItemDogBinding

class DogListItem(context: Context, private val clickCallback: (dog: Dog) -> Unit): FrameLayout(context) {

    private lateinit var dogListItem: Dog

    var binding: ListItemDogBinding =
        ListItemDogBinding.inflate(LayoutInflater.from(context), this, true)

    fun initView(dog: Dog) {
        dogListItem = dog
        binding.tvDogName.text = dog.name

        val dogPictureByteArray = dog.dogPicture
        if (dogPictureByteArray != null) {
            val bmp = BitmapFactory.decodeByteArray(dogPictureByteArray, 0, dogPictureByteArray.size)
            binding.ivDogPhoto.setImageBitmap(bmp)
        }

        binding.liLayout.setOnClickListener {
            clickCallback(dogListItem)
        }
    }
}