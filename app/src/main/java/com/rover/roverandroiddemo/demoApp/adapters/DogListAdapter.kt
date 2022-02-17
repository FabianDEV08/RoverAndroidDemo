package com.rover.roverandroiddemo.demoApp.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rover.roverandroiddemo.database.dog.Dog
import com.rover.roverandroiddemo.demoApp.dogList.dogListItem.DogListItem

class DogListAdapter(
    private var items: ArrayList<Dog>,
    private val clickCallback: (selectedDog: Dog) -> Unit,
    private val deleteClickCallback: (dog: Dog) -> Unit
): RecyclerView.Adapter<DogListAdapter.ViewHolder>() {

    class ViewHolder(val view: DogListItem): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DogListItem(parent.context, clickCallback, deleteClickCallback))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dogItem = items[position]
        holder.view.initView(dogItem)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateItems(dogs: ArrayList<Dog>) {
        items = dogs
        notifyDataSetChanged()
    }

    override fun onViewRecycled(holder: ViewHolder) {
        holder.view.recycleBitmap()
        super.onViewRecycled(holder)
    }
}