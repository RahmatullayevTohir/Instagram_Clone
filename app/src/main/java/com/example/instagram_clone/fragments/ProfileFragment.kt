package com.example.instagram_clone.fragments

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram_clone.R
import com.example.instagram_clone.adapter.ProfileAdapter
import com.example.instagram_clone.model.Post
import com.example.instagram_clone.utils.Logger
import com.google.android.material.imageview.ShapeableImageView
import com.sangcomz.fishbun.FishBun
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter

/**
 * In ProfileFragment posts that user uploaded can be seen and user is able to change his/her profile photo
 */

class ProfileFragment : BaseFragment() {
    val TAG = ProfileFragment::class.java.simpleName
    lateinit var rv_profile:RecyclerView

    var pickedPhoto: Uri? = null
    var allPhoto = ArrayList<Uri>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container,false)
        initViews(view)
        return view
    }

    fun initViews(view: View){
        rv_profile = view.findViewById(R.id.rv_profile)
        rv_profile.layoutManager = GridLayoutManager(activity,2)

        val iv_profile = view.findViewById<ShapeableImageView>(R.id.iv_profile)
        iv_profile.setOnClickListener { pickFishBunPhoto() }

        refreshAdapter(loadPosts())
    }

    /**
     * Pick photo using FishBun libary
     */

    private fun pickFishBunPhoto(){
        FishBun.with(this).setImageAdapter(GlideAdapter())
            .setMinCount(1)
            .setMinCount(1)
            .setSelectedImages(allPhoto)
            .startAlbumWithActivityResultCallback(photoLauncher)
    }

    private val photoLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode==Activity.RESULT_OK){
            allPhoto = it.data?.getParcelableArrayListExtra(FishBun.INTENT_PATH)?: arrayListOf()
            pickedPhoto = allPhoto.get(0)
            uploadPickedPhoto()
        }
    }

    private fun uploadPickedPhoto(){
        if (pickedPhoto!= null){
            Logger.d(TAG, pickedPhoto!!.path.toString())
        }
    }

    private fun refreshAdapter(items:ArrayList<Post>){
        val adapter = ProfileAdapter(this,items)
        rv_profile.adapter = adapter
    }

    private fun loadPosts():ArrayList<Post> {
        val items = ArrayList<Post>()
        items.add(Post("https://images.unsplash.com/photo-1500621137413-1a61d6ac1d44?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=388&q=80"))
        items.add(Post("https://images.unsplash.com/photo-1612596551578-9c81c9de1b3f?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=774&q=80"))
        items.add(Post("https://images.unsplash.com/photo-1597589827317-4c6d6e0a90bd?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=580&q=80"))

        items.add(Post("https://images.unsplash.com/photo-1500621137413-1a61d6ac1d44?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=388&q=80"))
        items.add(Post("https://images.unsplash.com/photo-1612596551578-9c81c9de1b3f?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=774&q=80"))
        items.add(Post("https://images.unsplash.com/photo-1597589827317-4c6d6e0a90bd?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=580&q=80"))

        return items
    }
}