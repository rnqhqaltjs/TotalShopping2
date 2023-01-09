package com.example.totalshopping2.ui.view

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.totalshopping2.databinding.FragmentSearchBinding
import com.example.totalshopping2.ui.adapter.ItemSearchAdapter
import com.example.totalshopping2.ui.viewmodel.ItemSearchViewModel
import com.example.totalshopping2.util.Constants.SEARCH_BOOKS_TIME_DELAY

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var itemSearchViewModel: ItemSearchViewModel
    private lateinit var itemSearchAdapter: ItemSearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemSearchViewModel = (activity as MainActivity).itemSearchViewModel

        setupRecyclerView()
        searchItems()

        itemSearchViewModel.searchResult.observe(viewLifecycleOwner) { response ->
            val items = response.items
            itemSearchAdapter.submitList(items)
        }
    }

    private fun setupRecyclerView() {
        itemSearchAdapter = ItemSearchAdapter()
        binding.rvSearchResult.apply {
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = itemSearchAdapter
        }
    }

    private fun searchItems() {
        var startTime = System.currentTimeMillis()
        var endTime: Long

        binding.etSearch.text =
            Editable.Factory.getInstance().newEditable(itemSearchViewModel.query)

        binding.etSearch.addTextChangedListener { text: Editable? ->
            endTime = System.currentTimeMillis()
            if (endTime - startTime >= SEARCH_BOOKS_TIME_DELAY) {
                text?.let {
                    val query = it.toString().trim()
                    if (query.isNotEmpty()) {
                        itemSearchViewModel.searchItems(query)
                        itemSearchViewModel.query = query
                    }
                }
            }
            startTime = endTime
        }
        itemSearchAdapter.setOnItemClickListener {
            val action = SearchFragmentDirections.actionFragmentSearchToFragmentItem(it)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}