package hu.bme.aut.moblab.memories.view.memory

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.moblab.memories.databinding.FragmentMemoryDetailsBinding
import hu.bme.aut.moblab.memories.viewmodel.MemoryDetailsViewModel

@AndroidEntryPoint
class MemoryDetailsFragment : Fragment() {
    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    private lateinit var binding: FragmentMemoryDetailsBinding
    private val viewModel: MemoryDetailsViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMemoryDetailsBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        getPermissions()



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.memory.observe(viewLifecycleOwner) {
            if (viewModel.memory.value?.imageUrls.isNullOrEmpty()) {
                Log.d("IMG_PATH", "IMG_PATH EMPTY")
            } else {
                Picasso.get().load("file:" + viewModel.memory.value!!.imageUrls).into(binding.ivMemoryImage)

                Log.d("IMG_PATH", "IMG_PATH IS: ${viewModel.memory.value?.imageUrls}")
            }
        }
    }

    private fun getPermissions() {
        val permission = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i("TAG", "Permission to record denied")
            ActivityCompat.requestPermissions(
                this.requireActivity(),
                PERMISSIONS_STORAGE,
                REQUEST_EXTERNAL_STORAGE
            );
        }
    }

}