package com.example.instagram_clone.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram_clone.R
import com.example.instagram_clone.adapter.FavoriteAdapter
import com.example.instagram_clone.adapter.HomeAdapter
import com.example.instagram_clone.model.Post

class FavoriteFragment : BaseFragment() {
    val TAG =FavoriteFragment::class.java.simpleName
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorite, container,false)
        initViews(view)
        return view
    }

    fun initViews(view: View){
        recyclerView = view.findViewById(R.id.recylerView_favorite)
        recyclerView.layoutManager = GridLayoutManager(activity,1)

        refreshAdapter(loadPosts())
    }

    private fun refreshAdapter(items:ArrayList<Post>){
        val adapter = FavoriteAdapter(this,items)
        recyclerView.adapter = adapter
    }

    private fun loadPosts():ArrayList<Post>{
        val items = ArrayList<Post>()
        return items
    }
}