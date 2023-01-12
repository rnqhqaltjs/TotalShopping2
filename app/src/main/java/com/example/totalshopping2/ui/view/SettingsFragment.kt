package com.example.totalshopping2.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.totalshopping2.R
import com.example.totalshopping2.databinding.FragmentSettingsBinding
import com.example.totalshopping2.ui.viewmodel.ItemSearchViewModel
import com.example.totalshopping2.util.Sort
import kotlinx.coroutines.launch

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private lateinit var itemSearchViewModel: ItemSearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemSearchViewModel = (activity as MainActivity).itemSearchViewModel

        saveSettings()
        loadSettings()
    }

    private fun saveSettings() {
        binding.rgSort.setOnCheckedChangeListener { _, checkedId ->
            val value = when (checkedId) {
                R.id.rb_sim -> Sort.SIM.value
                R.id.rb_date -> Sort.DATE.value
                R.id.rb_asc -> Sort.ASC.value
                R.id.rb_dsc -> Sort.DSC.value
                else -> return@setOnCheckedChangeListener
            }
            itemSearchViewModel.saveSortMode(value)
        }
    }

    private fun loadSettings() {
        lifecycleScope.launch {
            val buttonId = when (itemSearchViewModel.getSortMode()) {
                Sort.SIM.value -> R.id.rb_sim
                Sort.DATE.value -> R.id.rb_date
                Sort.ASC.value -> R.id.rb_asc
                Sort.DSC.value -> R.id.rb_dsc
                else -> return@launch
            }
            binding.rgSort.check(buttonId)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}