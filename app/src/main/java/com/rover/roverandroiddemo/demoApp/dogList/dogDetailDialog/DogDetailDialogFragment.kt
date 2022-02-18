package com.rover.roverandroiddemo.demoApp.dogList.dogDetailDialog

import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.rover.roverandroiddemo.R
import com.rover.roverandroiddemo.database.owner.OwnerAndDog
import com.rover.roverandroiddemo.databinding.DialogFragmentDogDetailBinding
import java.util.*

class DogDetailDialogFragment(private val dogDetails: OwnerAndDog): DialogFragment() {

    private var imageBitmap: Bitmap? = null

    private lateinit var binding: DialogFragmentDogDetailBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogFragmentDogDetailBinding.inflate(layoutInflater)
        return super.onCreateDialog(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Objects.requireNonNull(dialog)?.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        return binding.root
    }

    private fun displayDogPhoto(path: String) {
        Glide.with(requireContext())
            .load(path)
            .placeholder(R.drawable.ic_incognito_dog)
            .fitCenter()
            .into(binding.ivDogPhoto)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvDogName.text = dogDetails.dogName
        binding.tvDogBreed.text = dogDetails.dogBreed
        binding.tvDogAge.text = dogDetails.dogAge.toString()
        binding.tvDogSex.text = dogDetails.dogSex
        binding.tvOwnerName.text = dogDetails.ownerName
        binding.tvOwnerAge.text = dogDetails.ownerAge.toString()
        binding.tvOwnerAddress.text = dogDetails.ownerAddress
        binding.tvOwnerPhone.text = dogDetails.ownerPhone
        displayDogPhoto(dogDetails.dogPicture)
        binding.btClose.setOnClickListener {
            dialog?.dismiss()
        }
    }

    override fun onDestroyView() {
        imageBitmap?.recycle()
        imageBitmap = null
        super.onDestroyView()
    }
}