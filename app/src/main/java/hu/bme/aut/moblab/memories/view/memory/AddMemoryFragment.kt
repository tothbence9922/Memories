package hu.bme.aut.moblab.memories.view.memory

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.moblab.memories.R
import hu.bme.aut.moblab.memories.databinding.FragmentAddMemoryBinding
import hu.bme.aut.moblab.memories.viewmodel.AddMemoryViewModel

@AndroidEntryPoint
class AddMemoryFragment : Fragment() {

    private lateinit var binding: FragmentAddMemoryBinding
    private val viewModel: AddMemoryViewModel by viewModels()
    private val PICK_IMAGE = 100
    private var imageUri: Uri? = null

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

        binding.btAddImage.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            gallery.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            gallery.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
            startActivityForResult(gallery, PICK_IMAGE)
        }

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data?.data
            viewModel.imageUri.value = getRealPathFromURI(imageUri)
        }
    }

    fun getRealPathFromURI(contentUri: Uri?): String? {

        // can post image
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? =
            contentUri?.let { requireActivity().contentResolver.query(it, proj, null, null, null) }
        val column_index: Int? = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor?.moveToFirst()
        return column_index?.let { cursor?.getString(it) }
    }

}