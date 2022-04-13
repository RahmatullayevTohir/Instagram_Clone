package com.example.instagram_clone.fragments

import android.content.Context
import android.nfc.Tag
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram_clone.R
import com.example.instagram_clone.adapter.HomeAdapter
import com.example.instagram_clone.model.Post
import java.lang.RuntimeException

class HomeFragment : BaseFragment() {
    val TAG =HomeFragment::class.java.simpleName
    lateinit var recyclerView: RecyclerView
    private var listener:HomeListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container,false)
        initViews(view)
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = if (context is HomeListener){
            context
        }else{
            throw RuntimeException("$context must implement UploadListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    fun initViews(view: View){
        recyclerView = view.findViewById(R.id.recylerView)
        recyclerView.layoutManager = GridLayoutManager(activity,1)

        val iv_camera = view.findViewById<ImageView>(R.id.iv_camera)
        iv_camera.setOnClickListener { listener!!.scrollToUpload() }

        refreshAdapter(loadPosts())
    }

    private fun refreshAdapter(items:ArrayList<Post>){
        val adapter = HomeAdapter(this,items)
        recyclerView.adapter = adapter
    }

    private fun loadPosts():ArrayList<Post>{
        val items = ArrayList<Post>()
        items.add(Post("https://images.unsplash.com/photo-1500621137413-1a61d6ac1d44?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=388&q=80"))
        items.add(Post("https://images.unsplash.com/photo-1612596551578-9c81c9de1b3f?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=774&q=80"))
        items.add(Post("https://images.unsplash.com/photo-1597589827317-4c6d6e0a90bd?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=580&q=80"))
        return items
    }

    interface HomeListener{
        fun scrollToUpload()
    }

}