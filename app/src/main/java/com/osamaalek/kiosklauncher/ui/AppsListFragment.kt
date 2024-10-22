package com.osamaalek.kiosklauncher.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.osamaalek.kiosklauncher.R
import com.osamaalek.kiosklauncher.adapter.AppsAdapter
import com.osamaalek.kiosklauncher.util.AppsUtil

class AppsListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_apps_list, container, false)
        recyclerView = view.findViewById(R.id.recyclerView_apps)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 4)
        recyclerView.setHasFixedSize(true)

        // Get the list of apps and set the adapter
        val apps = AppsUtil.getAllApps(requireContext())
        recyclerView.adapter = AppsAdapter(apps, requireContext())

        return view
    }
}

