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
import com.example.instagram_clone.manager.AuthManager
import com.example.instagram_clone.manager.DatabaseManager
import com.example.instagram_clone.manager.DatabaseManager.deletePost
import com.example.instagram_clone.manager.handler.DBPostHandler
import com.example.instagram_clone.manager.handler.DBPostsHandler
import com.example.instagram_clone.model.Post
import com.example.instagram_clone.utils.DialogListener
import com.example.instagram_clone.utils.Utils

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

        loadLikeFeeds()
    }

    fun likeOrUnLikePost(post: Post){
        val uid = AuthManager.currentUser()!!.uid
        DatabaseManager.likeFeedPost(uid,post)

        loadLikeFeeds()
    }

    fun loadLikeFeeds(){
        showLoading(requireActivity())
        val uid = AuthManager.currentUser()!!.uid
        DatabaseManager.loadLikeFeeds(uid, object :DBPostsHandler{
            override fun onSuccess(posts: ArrayList<Post>) {
                dismissLoading()
                refreshAdapter(posts)
            }

            override fun onError(e: Exception) {
                dismissLoading()
            }
        })
    }

    fun showDeleteDialog(post: Post){
        Utils.dialogDouble(requireContext(), getString(R.string.str_delete_post), object :
            DialogListener {
            override fun onCallback(isChosen: Boolean) {
                if(isChosen){
                    deletePost(post)
                }
            }
        })
    }


    private fun refreshAdapter(items:ArrayList<Post>){
        val adapter = FavoriteAdapter(this,items)
        recyclerView.adapter = adapter
    }



    private fun loadPosts():ArrayList<Post>{
        val items = ArrayList<Post>()
        return items
    }

    fun deletePost(post: Post) {
        DatabaseManager.deletePost(post, object : DBPostHandler {
            override fun onSuccess(post: Post) {
                loadLikeFeeds()
            }

            override fun onError(e: java.lang.Exception) {

            }
        })
    }
}