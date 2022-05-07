package hu.bme.aut.moblab.memories.view.memories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.moblab.memories.R
import hu.bme.aut.moblab.memories.databinding.FragmentMemoriesBinding
import hu.bme.aut.moblab.memories.view.memories.adapter.MemoriesAdapter
import hu.bme.aut.moblab.memories.viewmodel.MemoriesViewModel


@AndroidEntryPoint
class MemoriesFragment : Fragment() {

    private lateinit var binding: FragmentMemoriesBinding
    private val viewModel: MemoriesViewModel by viewModels()
    private val adapter = MemoriesAdapter(
        { findNavController().navigate(MemoriesFragmentDirections.actionToMemoryDetails(it.memoryId)) },
        { viewModel.delete(it) }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMemoriesBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.fab.setOnClickListener { view ->
            view.findNavController().navigate(R.id.add_memory)
        }
        binding.swOnline.setOnClickListener {
            viewModel.toggleOnline()
        }



        with(binding) {
            listMemories.adapter = adapter
            listMemories.layoutManager = LinearLayoutManager(context)

        }

        viewModel.memories.observe(viewLifecycleOwner) { adapter.submitList(it) }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.refreshLayout.setOnRefreshListener {
            viewModel.getAllMemories()
            binding.refreshLayout.isRefreshing = false
        }

        val simpleItemTouchCallback: ItemTouchHelper.SimpleCallback =
            object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                    val swipedPosition = viewHolder.adapterPosition
                    viewModel.delete(viewModel.memories.value?.get(swipedPosition)!!)
                }
            }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(binding.listMemories)

    }

}