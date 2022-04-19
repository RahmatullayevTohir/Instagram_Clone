package com.example.instagram_clone.fragments

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagram_clone.R
import com.example.instagram_clone.activity.MainActivity
import com.example.instagram_clone.adapter.ProfileAdapter
import com.example.instagram_clone.manager.AuthManager
import com.example.instagram_clone.manager.DatabaseManager
import com.example.instagram_clone.manager.StorageManager
import com.example.instagram_clone.manager.handler.DBPostsHandler
import com.example.instagram_clone.manager.handler.DBUserHandler
import com.example.instagram_clone.manager.handler.DBUsersHandler
import com.example.instagram_clone.manager.handler.StorageHandler
import com.example.instagram_clone.model.Post
import com.example.instagram_clone.model.User
import com.example.instagram_clone.utils.Extension.toast
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
    lateinit var iv_profile:ShapeableImageView
    lateinit var tv_fullname:TextView
    lateinit var tv_email:TextView
    lateinit var tv_posts:TextView
    lateinit var tv_followers:TextView
    lateinit var tv_following:TextView

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
        rv_profile.layoutManager = GridLayoutManager(activity,3)

        tv_followers = view.findViewById(R.id.tv_followers)
        tv_following = view.findViewById(R.id.tv_following)
        tv_posts = view.findViewById(R.id.tv_posts)
        tv_fullname = view.findViewById(R.id.tv_fullname)
        tv_email = view.findViewById(R.id.tv_email)
        iv_profile = view.findViewById(R.id.iv_profile)
        iv_profile.setOnClickListener { pickFishBunPhoto() }

        val iv_logout = view.findViewById<ImageView>(R.id.iv_logout)
        iv_logout.setOnClickListener {
            AuthManager.signOut()
            callSignInActivity(requireActivity())
        }


        refreshAdapter(loadPosts())
        loadUserInfo()
        loadMyPosts()
        loadMyFollowing()
        loadMyFollowers()
    }

    private fun loadMyFollowing(){
        val uid = AuthManager.currentUser()!!.uid
        DatabaseManager.loadFollowing(uid, object :DBUsersHandler{
            override fun onSuccess(users: ArrayList<User>) {
                tv_following.text = users.size.toString()
            }

            override fun onError(e: Exception) {

            }
        })
    }

    private fun loadMyFollowers(){
        val uid = AuthManager.currentUser()!!.uid
        DatabaseManager.loadFollowers(uid, object :DBUsersHandler{
            override fun onSuccess(users: ArrayList<User>) {
                tv_followers.text = users.size.toString()
            }

            override fun onError(e: Exception) {

            }
        })
    }

    private fun loadMyPosts(){
        val uid = AuthManager.currentUser()!!.uid
        DatabaseManager.loadPosts(uid,object :DBPostsHandler{
            override fun onSuccess(posts: ArrayList<Post>) {
                tv_posts.text = posts.size.toString()
                refreshAdapter(posts)
            }

            override fun onError(e: Exception) {
                dismissLoading()
            }
        })
    }

    private fun showUserInfo(user: User){
        tv_fullname.text = user.fullname
        tv_email.text = user.email
        Glide.with(this)
            .load(user.userImg)
            .placeholder(R.drawable.im_profile)
            .error(R.drawable.im_profile)
            .into(iv_profile)

    }

    /**
     * Pick photo using FishBun libary
     */

    private fun pickFishBunPhoto(){
        FishBun.with(this).setImageAdapter(GlideAdapter())
            .setMinCount(1)
            .setMaxCount(1)
            .setSelectedImages(allPhoto)
            .startAlbumWithActivityResultCallback(photoLauncher)
    }

    private val photoLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode==Activity.RESULT_OK){
            allPhoto = it.data?.getParcelableArrayListExtra(FishBun.INTENT_PATH)?: arrayListOf()
            pickedPhoto = allPhoto[0]
            uploadUserPhoto()
        }
    }


    private fun loadUserInfo(){
        DatabaseManager.loadUser(AuthManager.currentUser()!!.uid, object :DBUserHandler{
            override fun onSuccess(user: User?) {
                if (user!=null){
                    showUserInfo(user)
                }
            }

            override fun onError(e: Exception) {

            }
        })
    }



    private fun uploadUserPhoto(){
        if (pickedPhoto == null) return
        StorageManager.uploadUserPhoto(pickedPhoto!!, object :StorageHandler{
            override fun onSuccess(imgUrl: String) {
                DatabaseManager.updateUserImage(imgUrl)
                (requireActivity() as MainActivity).toast(pickedPhoto.toString())
                iv_profile.setImageURI(pickedPhoto)
            }

            override fun onError(exception: Exception) {

            }
        })
    }

    private fun refreshAdapter(items:ArrayList<Post>){
        val adapter = ProfileAdapter(this,items)
        rv_profile.adapter = adapter
    }

    private fun loadPosts():ArrayList<Post> {
        val items = ArrayList<Post>()
        return items
    }
}