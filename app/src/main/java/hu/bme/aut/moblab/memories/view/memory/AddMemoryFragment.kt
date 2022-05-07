package hu.bme.aut.moblab.memories.view.memory

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.moblab.memories.R
import hu.bme.aut.moblab.memories.databinding.FragmentAddMemoryBinding
import hu.bme.aut.moblab.memories.databinding.FragmentMemoryDetailsBinding
import hu.bme.aut.moblab.memories.viewmodel.AddMemoryViewModel
import hu.bme.aut.moblab.memories.viewmodel.MemoryDetailsViewModel
import kotlinx.android.synthetic.main.fragment_add_memory.*
import javax.inject.Inject

@AndroidEntryPoint
class AddMemoryFragment : Fragment() {

    private lateinit var binding: FragmentAddMemoryBinding
    private val viewModel: AddMemoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddMemoryBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.btDismiss.setOnClickListener { view ->
            view.findNavController().navigate(R.id.memories)
        }
        binding.btAdd.setOnClickListener { view ->
            viewModel.addMemory()
            view.findNavController().navigate(R.id.memories)
        }
        binding.btRetrofitAdd.setOnClickListener { view ->
            viewModel.retrofitAddMemory()
            view.findNavController().navigate(R.id.memories)
        }
        return binding.root
    }

}