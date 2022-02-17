package com.rover.roverandroiddemo.demoApp.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rover.roverandroiddemo.database.DemoAppRepository
import com.rover.roverandroiddemo.database.dog.Dog
import com.rover.roverandroiddemo.database.getDatabase
import com.rover.roverandroiddemo.database.owner.Owner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddDogViewModel(application: Application): AndroidViewModel(application) {

    private val database by lazy {getDatabase(application)}
    private val repository by lazy {DemoAppRepository(database)}

    suspend fun insertOwner(owner: Owner): Long = withContext(Dispatchers.IO) {
        repository.insertOwner(owner)
    }

    suspend fun insertDog(dog: Dog): Long = withContext(Dispatchers.IO) {
        repository.insertDog(dog)
    }

    suspend fun getOwnersAlphabetically(): List<Owner> = withContext(Dispatchers.IO) {
        repository.getOwnersAlphabetically()
    }

    class Factory(private val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AddDogViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AddDogViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct AddDogViewModel")
        }
    }
}