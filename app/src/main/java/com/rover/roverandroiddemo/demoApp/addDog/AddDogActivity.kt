package com.rover.roverandroiddemo.demoApp.addDog

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.rover.roverandroiddemo.R
import com.rover.roverandroiddemo.database.dog.Dog
import com.rover.roverandroiddemo.database.owner.Owner
import com.rover.roverandroiddemo.databinding.ActivityAddDogBinding
import com.rover.roverandroiddemo.demoApp.utils.NotificationHelper.displayErrorSnackBar
import com.rover.roverandroiddemo.demoApp.utils.NotificationHelper.displaySuccessSnackBar
import com.rover.roverandroiddemo.demoApp.viewModels.AddDogViewModel
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

class AddDogActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAddDogBinding
    private val viewModel: AddDogViewModel by lazy {
        ViewModelProvider(this).get(AddDogViewModel::class.java)
    }

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val imageBitmap = data?.extras?.get("data") as Bitmap
            binding.ivDogPhoto.setImageBitmap(imageBitmap)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddDogBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.btAddDog.setOnClickListener {
            if (isFormValid(this@AddDogActivity)) {
                addDog()
            }
        }

        binding.btAddPhoto.setOnClickListener {
            dispatchTakePictureIntent()
        }
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        resultLauncher.launch(takePictureIntent)
    }

    private fun isFormValid(context: Context): Boolean {
        val parentView = binding.parentLayout
        if (binding.etDogName.text.isNullOrEmpty()) {
            binding.etDogName.requestFocus()
            displayErrorSnackBar(context, parentView, getString(R.string.dog_name_warning))
            return false
        }
        if (binding.etDogBreed.text.isNullOrEmpty()) {
            binding.etDogBreed.requestFocus()
            displayErrorSnackBar(context, parentView, getString(R.string.dog_breed_warning))
            return false
        }
        if (binding.etDogAge.text.isNullOrEmpty()) {
            binding.etDogAge.requestFocus()
            displayErrorSnackBar(context, parentView, getString(R.string.dog_age_warning))
            return false
        }
        if (binding.etDogSex.text.isNullOrEmpty()) {
            binding.etDogSex.requestFocus()
            displayErrorSnackBar(context, parentView, getString(R.string.dog_sex_warning))
            return false
        }

        if (binding.etOwnerName.text.isNullOrEmpty()) {
            binding.etOwnerName.requestFocus()
            displayErrorSnackBar(context, parentView, getString(R.string.owner_name_warning))
            return false
        }

        if (binding.etOwnerAge.text.isNullOrEmpty()) {
            binding.etOwnerAge.requestFocus()
            displayErrorSnackBar(context, parentView, getString(R.string.owner_age_warning))
            return false
        }

        if (binding.etOwnerAddress.text.isNullOrEmpty()) {
            binding.etOwnerAddress.requestFocus()
            displayErrorSnackBar(context, parentView, "Please enter owner address")
            return false
        }

        if (binding.etOwnerPhone.text.isNullOrEmpty()) {
            binding.etOwnerPhone.requestFocus()
            displayErrorSnackBar(context, parentView, "Please enter owner phone")
            return false
        }

        return true
    }

    private fun addDog() {
        val owner = Owner(
            binding.etOwnerName.text.toString(),
            binding.etOwnerAge.text.toString().toInt(),
            binding.etOwnerAddress.text.toString(),
            binding.etOwnerPhone.text.toString())
        lifecycleScope.launch {
            val insertedOwnerId = viewModel.insertOwner(owner)
            if(insertedOwnerId > -1L) {
                val bitmap = (binding.ivDogPhoto.drawable as BitmapDrawable).bitmap
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                val dog = Dog(
                    binding.etDogName.text.toString(),
                    binding.etDogBreed.text.toString(),
                    binding.etDogAge.text.toString().toInt(),
                    binding.etDogSex.text.toString(),
                    stream.toByteArray(),
                    insertedOwnerId.toInt()
                )
                val insertedDogId = viewModel.insertDog(dog)
                if (insertedDogId > -1) {
                    displaySuccessSnackBar(this@AddDogActivity, binding.parentLayout, getString(R.string.dog_added_message))
                    Handler(mainLooper).postDelayed({
                        finish()
                    }, 4000)
                } else {
                    displayErrorSnackBar(this@AddDogActivity, binding.parentLayout, getString(R.string.inserting_dog_error))
                }
            } else {
                displayErrorSnackBar(this@AddDogActivity, binding.parentLayout, getString(R.string.inserting_owner_error))
            }
        }
    }
}