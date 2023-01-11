package com.example.totalshopping2.ui.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.totalshopping2.databinding.FragmentItemBinding
import com.example.totalshopping2.ui.viewmodel.ItemSearchViewModel
import com.google.android.material.snackbar.Snackbar

class ItemFragment : Fragment() {
    private var _binding: FragmentItemBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<ItemFragmentArgs>()
    private lateinit var itemSearchViewModel: ItemSearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemSearchViewModel = (activity as MainActivity).itemSearchViewModel

        val item = args.item
        binding.webview.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            loadUrl(item.link.replace("search", "msearch").replace("gate.nhn?id=", "product/"))
        }

        binding.fabFavorite.setOnClickListener {
            itemSearchViewModel.saveItem(item)
            Snackbar.make(view, "Item has saved", Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onPause() {
        binding.webview.onPause()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        binding.webview.onResume()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}