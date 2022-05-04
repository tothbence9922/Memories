package hu.bme.aut.moblab.memories.view.memories

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.getInputField
import com.afollestad.materialdialogs.input.input
import hu.bme.aut.moblab.memories.R
import hu.bme.aut.moblab.memories.model.db.Memory
import hu.bme.aut.moblab.memories.view.memories.adapter.MemoryAdapter
import hu.bme.aut.moblab.memories.viewmodel.MemoriesViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var memoryAdapter: MemoryAdapter
    private val memoriesViewModel: MemoriesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            showAddMemoryDialog()
        }

        initRecyclerView()
    }

    private fun initRecyclerView() {
        memoryAdapter = MemoryAdapter(this, memoriesViewModel)
        listMemories.adapter = memoryAdapter


        memoriesViewModel.allMemories.observe(this, {
                memories -> memoryAdapter.submitList(memories)
        })
    }

    private fun showAddMemoryDialog() {
        MaterialDialog(this).show {
            noAutoDismiss()
            title(text = getString(R.string.title_memory_add_dialog))
            input()

            positiveButton(text=getString(R.string.btn_add)) {
                val memoryTitle = it.getInputField().text.toString()
                if (memoryTitle.isNotEmpty()) {

                    var id = UUID.randomUUID().toString()

                    //saveMemory(Memory(null, memoryName))
                    memoriesViewModel.insert(Memory(id, memoryTitle, "Description", ""))

                    it.dismiss()
                } else {
                    it.getInputField().error = getString(R.string.error_empty_field)
                }
            }
            negativeButton(text=getString(R.string.btn_dismiss)) {
                it.dismiss()
            }
        }
    }

}
