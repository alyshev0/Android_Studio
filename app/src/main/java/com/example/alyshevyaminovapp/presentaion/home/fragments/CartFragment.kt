package com.example.alyshevyaminovapp.presentaion.home.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.alyshevyaminovapp.R
import com.example.alyshevyaminovapp.ViewModel.SharedViewModel
import com.example.alyshevyaminovapp.domain.Adapters.CartAdapter

class CartFragment : Fragment() {
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CartAdapter
    private lateinit var totalCostTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_cart, container, false)

        // Инициализация ViewModel
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        totalCostTextView = view.findViewById(R.id.total_item_cost)

        adapter = CartAdapter(
            emptyList(),
        onHeaderImageClick = { item ->
            sharedViewModel.showDetails(requireContext(), item)
        },
        onDeleteClick = { item ->
            sharedViewModel.removeItem(item)
        },
        onIncreaseQuantity = { item ->
            sharedViewModel.increaseQuantity(item)
        },
        onDecreaseQuantity = { item ->
            sharedViewModel.decreaseQuantity(item)
        })

        recyclerView.adapter = adapter
        sharedViewModel.selectedItems.observe(viewLifecycleOwner) { items ->
            adapter.updateItems(items)
        }
        sharedViewModel.totalCost.observe(viewLifecycleOwner) { total ->
            totalCostTextView.text = "Общая сумма: $total"
        }
        return view
    }
}