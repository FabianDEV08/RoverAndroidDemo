package com.rover.roverandroiddemo.demoApp.addDog

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.rover.roverandroiddemo.R
import com.rover.roverandroiddemo.database.dog.Dog
import com.rover.roverandroiddemo.database.owner.Owner
import com.rover.roverandroiddemo.databinding.ActivityAddDogBinding
import com.rover.roverandroiddemo.demoApp.utils.NotificationHelper.displayErrorSnackBar
import com.rover.roverandroiddemo.demoApp.utils.NotificationHelper.displaySuccessSnackBar
import com.rover.roverandroiddemo.demoApp.viewModels.AddDogViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.*
import java.util.stream.Collectors

class AddDogActivity: AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: ActivityAddDogBinding
    private var selectedOwner: Owner? = null
    private var selectedSex: String? = null
    private var dogPhoto: Bitmap? = null
    private lateinit var ownerList: ArrayList<Owner>
    private val viewModel: AddDogViewModel by viewModels { AddDogViewModel.Factory(application) }
    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            checkBitmap()
            dogPhoto = data?.extras?.get("data") as Bitmap
            binding.ivDogPhoto.setImageBitmap(dogPhoto)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddDogBinding.inflate(layoutInflater)
        binding.btAddDog.setOnClickListener(addDogClickListener())
        binding.btAddDog2.setOnClickListener(addDogClickListener())
        binding.btAddPhoto.setOnClickListener {
            dispatchTakePictureIntent()
        }
        binding.spOwnerSelect.onItemSelectedListener = this
        binding.rbMale.setOnClickListener {
            if ((it as RadioButton).isChecked) {
                selectedSex = "male"
            }
        }
        binding.rbFemale.setOnClickListener {
            if ((it as RadioButton).isChecked) {
                selectedSex = "female"
            }
        }
        lifecycleScope.launch {
            val ownerNameList = arrayListOf("Select...")
            val owners = viewModel.getOwnersAlphabetically()
            if (owners.isNotEmpty()) {
                ownerList = ArrayList(owners)
                val ownerNames = ownerList.stream().map(Owner::name).collect(Collectors.toList())
                ownerNameList.addAll(ownerNames)
            }
            val adapter = ArrayAdapter(applicationContext, R.layout.spinner_owner_names, ownerNameList)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spOwnerSelect.adapter = adapter
        }
        setContentView(binding.root)
    }

    override fun onDestroy() {
        checkBitmap()
        super.onDestroy()
    }

    private fun checkBitmap() {
        dogPhoto?.recycle()
        dogPhoto = null
    }

    private fun addDogClickListener(): View.OnClickListener {
        return View.OnClickListener {
            if (isFormValid(this@AddDogActivity)) {
                actionAddDog()
            }
        }
    }

    private fun getOwnerIdByName(name: String): Owner? {
        return ownerList.find { it.name == name }
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        resultLauncher.launch(takePictureIntent)
    }

    private fun isFormValid(context: Context): Boolean {
        val parentView = binding.parentLayout
        parentView.clearFocus()
        if (dogPhoto == null) {
            displayErrorSnackBar(context, parentView, getString(R.string.take_photo_warning))
            return false
        }
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
        if (selectedSex == null) {
            binding.rbMale.requestFocus()
            displayErrorSnackBar(context, parentView, getString(R.string.dog_sex_warning))
            return false
        }
        if (selectedOwner != null) {
            return true
        }
        if (binding.etOwnerName.text.isNullOrEmpty()) {
            binding.etOwnerName.requestFocus()
            displayErrorSnackBar(context, parentView, getString(R.string.owner_warning))
            return false
        }
        if (binding.etOwnerAge.text.isNullOrEmpty()) {
            binding.etOwnerAge.requestFocus()
            displayErrorSnackBar(context, parentView, getString(R.string.owner_age_warning))
            return false
        }
        if (binding.etOwnerAddress.text.isNullOrEmpty()) {
            binding.etOwnerAddress.requestFocus()
            displayErrorSnackBar(context, parentView, getString(R.string.owner_address_warning))
            return false
        }
        val ownerPhoneEditText = binding.etOwnerPhone
        if (ownerPhoneEditText.text.isNullOrEmpty() || ownerPhoneEditText.text.toString().length != 10) {
            ownerPhoneEditText.requestFocus()
            displayErrorSnackBar(context, parentView, getString(R.string.owner_phone_warning))
            return false
        }
        return true
    }

    private fun insertDog(ownerId: Int) = runBlocking {
        val dogPhotoPath = saveDogPictureAndReturnPath() ?: return@runBlocking
        val stream = ByteArrayOutputStream()
        dogPhoto!!.compress(Bitmap.CompressFormat.PNG, 10, stream)
        val dog = Dog(
            binding.etDogName.text.toString(),
            binding.etDogBreed.text.toString(),
            binding.etDogAge.text.toString().toInt(),
            selectedSex!!,
            dogPhotoPath,
            ownerId
        )
        val insertedDogId = viewModel.insertDog(dog)
        if (insertedDogId > 0) {
            displaySuccessSnackBar(this@AddDogActivity, binding.parentLayout, getString(R.string.dog_added_message))
            Handler(mainLooper).postDelayed({
                finish()
            }, 2500)
        } else {
            displayErrorSnackBar(this@AddDogActivity, binding.parentLayout, getString(R.string.inserting_dog_error))
        }
    }

    private fun saveDogPictureAndReturnPath(): String? {
        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat.getDateTimeInstance()
        val formattedDate = formatter.format(date).replace(" ", "_")
        return try {
            val storagePath = applicationContext.getExternalFilesDir(null)?.absolutePath
                ?: applicationContext.externalCacheDir?.absolutePath
            val dogName = binding.etDogName.text.toString()
            val fileName = storagePath + File.separator + dogName + "_${formattedDate}.jpg"
            val dogPhotoFile = File(fileName)
            if (!dogPhotoFile.exists()) {
                val fos = FileOutputStream(dogPhotoFile)
                dogPhoto!!.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                fos.flush()
                fos.close()
            }
            return fileName
        } catch (ex: Exception) {
            displayErrorSnackBar(applicationContext, binding.parentLayout, getString(R.string.dog_photo_save_error))
            null
        }
    }

    private fun actionAddDog() {
        if (selectedOwner != null) {
            insertDog(selectedOwner!!.id)
        } else {
            lifecycleScope.launch {
                val owner = Owner(
                    binding.etOwnerName.text.toString(),
                    binding.etOwnerAge.text.toString().toInt(),
                    binding.etOwnerAddress.text.toString(),
                    binding.etOwnerPhone.text.toString())
                val insertedOwnerId = viewModel.insertOwner(owner)
                if (insertedOwnerId > 0L) {
                    insertDog(insertedOwnerId.toInt())
                } else {
                    displayErrorSnackBar(this@AddDogActivity, binding.parentLayout, getString(R.string.inserting_owner_error))
                }
            }
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (position > 0) {
            selectedOwner = getOwnerIdByName(binding.spOwnerSelect.selectedItem.toString())
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        selectedOwner = null
    }
}