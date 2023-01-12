package com.example.totalshopping2.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.totalshopping2.databinding.FragmentFavoriteBinding
import com.example.totalshopping2.ui.adapter.ItemSearchAdapter
import com.example.totalshopping2.ui.viewmodel.ItemSearchViewModel
import com.example.totalshopping2.util.collectLatestStateFlow
import com.google.android.material.snackbar.Snackbar

class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private lateinit var itemSearchViewModel: ItemSearchViewModel
    private lateinit var itemSearchAdapter: ItemSearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemSearchViewModel = (activity as MainActivity).itemSearchViewModel

        setupRecyclerView()
        setupTouchHelper(view)

//        itemSearchViewModel.favoriteItems.observe(viewLifecycleOwner) {
//            itemSearchAdapter.submitList(it)
//        }

//        lifecycleScope.launch {
//            itemSearchViewModel.favoriteItems.collectLatest {
//                itemSearchAdapter.submitList(it)
//            }
//        }

//        viewLifecycleOwner.lifecycleScope.launch {
//            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                itemSearchViewModel.favoriteItems.collectLatest {
//                    itemSearchAdapter.submitList(it)
//                }
//            }
//        }

        collectLatestStateFlow(itemSearchViewModel.favoriteItems) {
            itemSearchAdapter.submitList(it)
        }
    }

    private fun setupRecyclerView() {
        itemSearchAdapter = ItemSearchAdapter()
        binding.rvFavoriteItems.apply {
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
        itemSearchAdapter.setOnItemClickListener {
            val action = FavoriteFragmentDirections.actionFragmentFavoriteToFragmentItem(it)
            findNavController().navigate(action)
        }
    }

    private fun setupTouchHelper(view: View) {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                val item = itemSearchAdapter.currentList[position]
                itemSearchViewModel.deleteItem(item)
                Snackbar.make(view, "Item has deleted", Snackbar.LENGTH_SHORT).apply {
                    setAction("Undo") {
                        itemSearchViewModel.saveItem(item)
                    }
                }.show()
            }
        }
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.rvFavoriteItems)
        }
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}