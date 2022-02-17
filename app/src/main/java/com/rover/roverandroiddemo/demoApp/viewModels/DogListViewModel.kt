package com.rover.roverandroiddemo.demoApp.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.rover.roverandroiddemo.database.DemoAppRepository
import com.rover.roverandroiddemo.database.getDatabase

class DogListViewModel(application: Application): AndroidViewModel(application) {

    private val database by lazy { getDatabase(application) }
    private val repository by lazy { DemoAppRepository(database) }

    val allDogs = repository.allDogs.asLiveData()

    suspend fun getDogAndOwnerByIds(dogId: Int, ownerId: Int) = repository.getDogAndOwnerByIds(dogId, ownerId)

    suspend fun deleteDog(id: Int) = repository.deleteDogById(id)

    class Factory(private val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DogListViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DogListViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct DogListViewModel")
        }
    }
}