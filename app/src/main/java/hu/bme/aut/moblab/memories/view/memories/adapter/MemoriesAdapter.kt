package hu.bme.aut.moblab.memories.view.memories.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import hu.bme.aut.moblab.memories.databinding.MemoryRowBinding
import hu.bme.aut.moblab.memories.model.dto.MemoryDTO

class MemoriesAdapter(
    private val navigate: (MemoryDTO) -> Unit,
    private val delete: (MemoryDTO) -> Unit
) : ListAdapter<MemoryDTO, MemoriesAdapter.MemoryViewHolder>(MemoryDiffCallback()) {

    class MemoryViewHolder(
        private val binding: MemoryRowBinding,
        private val navigate: (MemoryDTO) -> Unit,
        private val delete: (MemoryDTO) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(memory: MemoryDTO) {
            binding.memoryDTO = memory
            binding.item.setOnClickListener { navigate(memory) }

            if (memory.imageUrls.isNullOrEmpty()) {
                Log.d("IMG_PATH", "IMG_PATH EMPTY")
            } else {
                Picasso.get().load(memory.imageUrls)
                    .into(binding.ivMemoryImage)

                Log.d("IMG_PATH", "IMG_PATH IS: ${memory.imageUrls}")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MemoryViewHolder(
            MemoryRowBinding.inflate(layoutInflater, parent, false),
            navigate,
            delete
        )
    }

    override fun onBindViewHolder(holder: MemoryViewHolder, position: Int) {
        holder.bind(getItem(position))

    }
}

class MemoryDiffCallback : DiffUtil.ItemCallback<MemoryDTO>() {
    override fun areItemsTheSame(oldItem: MemoryDTO, newItem: MemoryDTO): Boolean {
        return oldItem.memoryId == newItem.memoryId
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: MemoryDTO, newItem: MemoryDTO): Boolean {
        return oldItem == newItem
    }
}