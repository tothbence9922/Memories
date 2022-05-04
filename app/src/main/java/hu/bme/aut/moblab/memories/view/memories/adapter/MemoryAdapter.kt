package hu.bme.aut.moblab.memories.view.memories.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.moblab.memories.R
import hu.bme.aut.moblab.memories.databinding.MemoryRowBinding
import hu.bme.aut.moblab.memories.model.db.Memory
import hu.bme.aut.moblab.memories.view.memory.MemoryDetailsActivity
import hu.bme.aut.moblab.memories.viewmodel.MemoriesViewModel

class MemoryAdapter(private val context: Context,
                  private val citiesViewModel: MemoriesViewModel
) : ListAdapter<Memory, MemoryAdapter.ViewHolder>(MemoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val binding: MemoryRowBinding = DataBindingUtil.inflate(layoutInflater,
            R.layout.memory_row,
            parent, false)
        binding.adapter = this
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val memory = getItem(position)
        holder.bind(memory)
    }

    fun deleteMemory(memory: Memory) {
        citiesViewModel.delete(memory)
    }

    fun showDetails(memory: Memory) {
        val intent = Intent(context, MemoryDetailsActivity::class.java)
        intent.putExtra(MemoryDetailsActivity.KEY_MEMORY, memory.memoryId)
        context.startActivity(intent)
    }

    class ViewHolder(val binding: MemoryRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(memory: Memory) {
            binding.memory = memory
        }
    }
}

class MemoryDiffCallback : DiffUtil.ItemCallback<Memory>() {
    override fun areItemsTheSame(oldItem: Memory, newItem: Memory): Boolean {
        return oldItem.memoryId == newItem.memoryId
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Memory, newItem: Memory): Boolean {
        return oldItem == newItem
    }
}