package com.rover.roverandroiddemo.demoApp.dogList.dogDetailDialog

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.rover.roverandroiddemo.R
import com.rover.roverandroiddemo.database.owner.OwnerAndDog
import java.util.*


class DogDetailDialogFragment(private val dogDetails: OwnerAndDog): DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Objects.requireNonNull(dialog)?.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        return inflater.inflate(R.layout.dialog_fragment_dog_detail,null,true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.tvDogName).text = dogDetails.dogName
        view.findViewById<TextView>(R.id.tvDogBreed).text = dogDetails.dogBreed
        view.findViewById<TextView>(R.id.tvDogAge).text = dogDetails.dogAge.toString()
        view.findViewById<TextView>(R.id.tvDogSex).text = dogDetails.dogSex
        view.findViewById<TextView>(R.id.tvOwnerName).text = dogDetails.ownerName
        view.findViewById<TextView>(R.id.tvOwnerAge).text = dogDetails.ownerAge.toString()
        view.findViewById<TextView>(R.id.tvOwnerAddress).text = dogDetails.ownerAddress
        view.findViewById<TextView>(R.id.tvOwnerPhone).text = dogDetails.ownerPhone

        val dogPictureByteArray = dogDetails.dogPicture
        if (dogPictureByteArray != null) {
            val bmp = BitmapFactory.decodeByteArray(dogPictureByteArray, 0, dogPictureByteArray.size)
            view.findViewById<ImageView>(R.id.ivDogPhoto).setImageBitmap(bmp)
        }

        view.findViewById<Button>(R.id.btClose).setOnClickListener {
            dialog?.dismiss()
        }
    }
}