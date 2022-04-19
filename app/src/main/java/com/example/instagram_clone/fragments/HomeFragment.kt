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
import com.example.instagram_clone.manager.AuthManager
import com.example.instagram_clone.manager.DatabaseManager
import com.example.instagram_clone.manager.handler.DBPostsHandler
import com.example.instagram_clone.model.Post
import java.lang.RuntimeException

class HomeFragment : BaseFragment() {
    val TAG =HomeFragment::class.java.simpleName
    lateinit var recyclerView: RecyclerView
    private var listener:HomeListener? = null
    var feeds = ArrayList<Post>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container,false)
        initViews(view)
        return view
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {

        if (isVisibleToUser && feeds.size>0){
            loadMyFeeds()
        }
    }

    /**
     * onAttach is for comunication of Fragments
     */
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

        loadMyFeeds()
    }

    private fun refreshAdapter(items:ArrayList<Post>){
        val adapter = HomeAdapter(this,items)
        recyclerView.adapter = adapter
    }

    private fun loadMyFeeds(){
        showLoading(requireActivity())
        val uid = AuthManager.currentUser()!!.uid
        DatabaseManager.loadFeeds(uid,object :DBPostsHandler{
            override fun onSuccess(posts: ArrayList<Post>) {
                dismissLoading()
                feeds.clear()
                feeds.addAll(posts)
                refreshAdapter(feeds)
            }

            override fun onError(e: Exception) {
                dismissLoading()
            }
        })
    }



    interface HomeListener{
        fun scrollToUpload()
    }

}