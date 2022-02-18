package com.rover.roverandroiddemo.demoApp.dogList.dogListItem

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.rover.roverandroiddemo.database.dog.Dog
import com.rover.roverandroiddemo.databinding.ListItemDogBinding

class DogListItem(
    context: Context,
    private val clickCallback: (dog: Dog) -> Unit,
    private val deleteCallback: (dog: Dog) -> Unit): FrameLayout(context) {
    private var imageBitmap: Bitmap? = null

    var binding: ListItemDogBinding =
        ListItemDogBinding.inflate(LayoutInflater.from(context), this, true)

    fun initView(dog: Dog) {
        binding.tvDogName.text = dog.name
        val dogPictureByteArray = dog.dogPicture
        if (dogPictureByteArray != null) {
            imageBitmap = BitmapFactory.decodeByteArray(dogPictureByteArray, 0, dogPictureByteArray.size)
            binding.ivDogPhoto.setImageBitmap(imageBitmap)
        }
        binding.liLayout.setOnClickListener {
            clickCallback(dog)
        }
        binding.ibDelete.setOnClickListener {
            deleteCallback(dog)
        }
    }

    fun recycleBitmap() {
        imageBitmap?.recycle()
        imageBitmap = null
    }
}