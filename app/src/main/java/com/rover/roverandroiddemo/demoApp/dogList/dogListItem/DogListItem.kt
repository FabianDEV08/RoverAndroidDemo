package com.rover.roverandroiddemo.demoApp.dogList.dogListItem

import android.content.Context
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
        binding.liLayout.setOnClickListener {
            clickCallback(dogListItem)
        }
    }

    fun getDogData(): Dog {
        return dogListItem
    }

}