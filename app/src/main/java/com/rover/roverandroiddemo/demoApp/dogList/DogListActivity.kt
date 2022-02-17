package com.rover.roverandroiddemo.demoApp.dogList

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rover.roverandroiddemo.database.dog.Dog
import com.rover.roverandroiddemo.databinding.ActivityDogListBinding
import com.rover.roverandroiddemo.demoApp.adapters.DogListAdapter
import com.rover.roverandroiddemo.demoApp.addDog.AddDogActivity
import com.rover.roverandroiddemo.demoApp.dogList.dogDetailDialog.DogDetailDialogFragment
import com.rover.roverandroiddemo.demoApp.viewModels.DogListViewModel
import kotlinx.coroutines.launch

class DogListActivity: AppCompatActivity() {

    private lateinit var listAdapter: DogListAdapter
    private lateinit var binding: ActivityDogListBinding
    private lateinit var recyclerView: RecyclerView
    private val dogListViewModel: DogListViewModel by viewModels { DogListViewModel.Factory(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDogListBinding.inflate(layoutInflater)
        recyclerView = binding.rvDogList
        recyclerView.layoutManager = GridLayoutManager(this, 1)
        recyclerView.setHasFixedSize(true)
        listAdapter = DogListAdapter(arrayListOf(), { showDogDetail(it) }) {
            lifecycleScope.launch {
                dogListViewModel.deleteDog(it.id)
            }
        }
        recyclerView.adapter = listAdapter
        dogListViewModel.allDogs.observe(this) { dogsList ->
            if (dogsList.isNotEmpty()) {
                binding.tvNoDogsAdded.visibility = View.GONE
            } else {
                binding.tvNoDogsAdded.visibility = View.VISIBLE
            }
            val dogs = arrayListOf<Dog>()
            dogs.addAll(dogsList)
            listAdapter.updateItems(dogs)
        }
        binding.fab.setOnClickListener {
            startActivity(Intent(this@DogListActivity, AddDogActivity::class.java))
        }
        setContentView(binding.root)
    }

    private fun showDogDetail(dog: Dog) {
        lifecycleScope.launch {
            val dogAndOwner = dogListViewModel.getDogAndOwnerByIds(dog.id, dog.ownerId).first()
            val dogDetail = DogDetailDialogFragment(dogAndOwner)
            dogDetail.show(supportFragmentManager, "detail_dialog")
        }
    }
}