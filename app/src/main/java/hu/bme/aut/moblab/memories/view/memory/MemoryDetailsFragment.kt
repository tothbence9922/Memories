package hu.bme.aut.moblab.memories.view.memory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.moblab.memories.databinding.FragmentMemoryDetailsBinding
import hu.bme.aut.moblab.memories.viewmodel.MemoryDetailsViewModel

@AndroidEntryPoint
class MemoryDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMemoryDetailsBinding
    private val viewModel: MemoryDetailsViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMemoryDetailsBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

}