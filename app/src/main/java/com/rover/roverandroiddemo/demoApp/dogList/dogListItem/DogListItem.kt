package com.rover.roverandroiddemo.demoApp.dogList.dogListItem

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.bumptech.glide.Glide
import com.rover.roverandroiddemo.R
import com.rover.roverandroiddemo.database.dog.Dog
import com.rover.roverandroiddemo.databinding.ListItemDogBinding

class DogListItem(
    context: Context,
    private val clickCallback: (dog: Dog) -> Unit,
    private val deleteCallback: (dog: Dog) -> Unit): FrameLayout(context) {

    var binding: ListItemDogBinding =
        ListItemDogBinding.inflate(LayoutInflater.from(context), this, true)

    private fun displayDogPhoto(path: String) {
        Glide.with(context)
            .load(path)
            .placeholder(R.drawable.ic_incognito_dog)
            .fitCenter()
            .into(binding.ivDogPhoto)
    }

    fun initView(dog: Dog) {
        binding.tvDogName.text = dog.name
        displayDogPhoto(dog.dogPicturePath)
        binding.liLayout.setOnClickListener {
            clickCallback(dog)
        }
        binding.ibDelete.setOnClickListener {
            deleteCallback(dog)
        }
    }
}