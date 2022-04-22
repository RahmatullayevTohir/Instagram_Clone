package com.example.instagram_clone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagram_clone.R
import com.example.instagram_clone.fragments.HomeFragment
import com.example.instagram_clone.model.Post
import com.example.instagram_clone.model.User
import com.google.android.material.imageview.ShapeableImageView

class StoryAdapter(var fragment: HomeFragment, var items:ArrayList<User>):BaseAdapter() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_story_post, parent, false)
        return StoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val user:User = items[position]
        if (holder is StoryViewHolder){
            var iv_profile = holder.iv_profile
            var tv_fullname = holder.tv_fullname

            Glide
                .with(fragment)
                .load(user.userImg)
                .into(iv_profile)
            tv_fullname.text = user.fullname

        }
    }

    class StoryViewHolder(view: View):RecyclerView.ViewHolder(view){
        val iv_profile:ShapeableImageView
        val tv_fullname:TextView

        init {
            iv_profile = view.findViewById(R.id.iv_profile)
            tv_fullname = view.findViewById(R.id.tv_fullname)
        }
    }
}