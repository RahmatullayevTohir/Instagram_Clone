package com.example.instagram_clone.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.instagram_clone.R
import com.example.instagram_clone.adapter.HomeAdapter
import com.example.instagram_clone.adapter.StoryAdapter
import com.example.instagram_clone.manager.AuthManager
import com.example.instagram_clone.manager.DatabaseManager
import com.example.instagram_clone.manager.handler.DBPostHandler
import com.example.instagram_clone.manager.handler.DBPostsHandler
import com.example.instagram_clone.manager.handler.DBUserHandler
import com.example.instagram_clone.manager.handler.DBUsersHandler
import com.example.instagram_clone.model.Post
import com.example.instagram_clone.model.User
import com.example.instagram_clone.utils.DialogListener
import com.example.instagram_clone.utils.Logger
import com.example.instagram_clone.utils.Utils
import java.lang.RuntimeException

class HomeFragment : BaseFragment() {
    val TAG = HomeFragment::class.java.simpleName
    lateinit var recyclerView: RecyclerView
    lateinit var rv_stores: RecyclerView
    private var listener: HomeListener? = null
    var feeds = ArrayList<Post>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        initViews(view)
        initStoryView(view)
        return view
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {

        if (isVisibleToUser && feeds.size > 0) {
            loadMyFeeds()
        }
    }

    /**
     * onAttach is for comunication of Fragments
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = if (context is HomeListener) {
            context
        } else {
            throw RuntimeException("$context must implement UploadListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    fun initViews(view: View) {
        recyclerView = view.findViewById(R.id.recylerView)
        recyclerView.layoutManager = GridLayoutManager(activity, 1)

        val iv_camera = view.findViewById<ImageView>(R.id.iv_camera)
        iv_camera.setOnClickListener { listener!!.scrollToUpload() }

        loadMyFeeds()
    }

    private fun initStoryView(view: View) {
        rv_stores = view.findViewById(R.id.rv_stores)
        rv_stores.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        loadStores()
//        refreshAdapterStory(loadStores())
    }

    private fun loadStores(): ArrayList<User> {
        var items = ArrayList<User>()
        DatabaseManager.loadUsers(object : DBUsersHandler {
            override fun onSuccess(users: ArrayList<User>) {
//                items.addAll(users)
                refreshAdapterStory(users)
            }

            override fun onError(e: Exception) {
            }

        })
        return items
    }

    private fun refreshAdapterStory(items: ArrayList<User>) {
        val adapter = StoryAdapter(this, items)
        Logger.d("@@@", items.size.toString())
        rv_stores.adapter = adapter
    }

    private fun refreshAdapter(items: ArrayList<Post>) {
        val adapter = HomeAdapter(this, items)
        recyclerView.adapter = adapter
    }

    private fun loadMyFeeds() {
        showLoading(requireActivity())
        val uid = AuthManager.currentUser()!!.uid
        DatabaseManager.loadFeeds(uid, object : DBPostsHandler {
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

    fun likeOrUnLikePost(post: Post) {
        val uid = AuthManager.currentUser()!!.uid
        DatabaseManager.likeFeedPost(uid, post)

        DatabaseManager.loadUser(uid, object :DBUserHandler{
            override fun onSuccess(user: User?) {
                if (user!!.device_token != post.device_token){
                    val title = getString(R.string.str_liked_node)
                    Utils.sendNote(requireContext(), user, post.device_token)
                }
            }

            override fun onError(e: Exception) {

            }
        })
    }

    fun showDeleteDialog(post: Post) {
        Utils.dialogDouble(requireContext(), getString(R.string.str_delete_post), object :
            DialogListener {
            override fun onCallback(isChosen: Boolean) {
                if (isChosen) {
                    deletePost(post)
                }
            }
        })
    }

    fun deletePost(post: Post) {
        DatabaseManager.deletePost(post, object : DBPostHandler {
            override fun onSuccess(post: Post) {
                loadMyFeeds()
            }

            override fun onError(e: java.lang.Exception) {

            }
        })
    }


    interface HomeListener {
        fun scrollToUpload()
    }

}