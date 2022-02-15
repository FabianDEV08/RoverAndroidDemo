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

    private var listAdapter: DogListAdapter? = null
    private lateinit var binding: ActivityDogListBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var dogsFromDatabase: ArrayList<Dog>

    private val dogListViewModel: DogListViewModel by viewModels {
        DogListViewModel.Factory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDogListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.rvDogList
        recyclerView.layoutManager = GridLayoutManager(this, 1)
        recyclerView.setHasFixedSize(true)

        dogListViewModel.allDogs.observe(this) { dogsList ->
            if (dogsList.isNotEmpty()) {
                binding.tvNoDogsAdded.visibility = View.GONE
            }
            val arrayList: ArrayList<Dog> = arrayListOf()
            arrayList.addAll(dogsList)
            dogsFromDatabase = arrayList
            listAdapter = DogListAdapter(getDogsCopy()) {
                showDogDetail(it)
            }
            recyclerView.adapter = listAdapter
        }

        binding.fab.setOnClickListener {
            startActivity(Intent(this@DogListActivity, AddDogActivity::class.java))
        }
    }

    private fun showDogDetail(dog: Dog) {
        lifecycleScope.launch {
            val dogAndOwner = dogListViewModel.getDogAndOwnerByDogId(dog.id).first()
            val dogDetail: DogDetailDialogFragment = DogDetailDialogFragment(dogAndOwner)
            dogDetail.show(supportFragmentManager, "detail_dialog")
        }
    }

    private fun getDogsCopy(): ArrayList<Dog> {
        return if(dogsFromDatabase.isEmpty()) {
            arrayListOf()
        } else {
            arrayListOf<Dog>().apply { addAll(dogsFromDatabase) }
        }
    }
}