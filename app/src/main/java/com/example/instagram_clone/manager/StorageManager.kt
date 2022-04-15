package com.example.instagram_clone.manager

import android.net.Uri
import com.example.instagram_clone.manager.handler.StorageHandler
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask

private var USER_PHOTO_PATH ="users"
private var POST_PHOTO_PATH ="posts"

object StorageManager {
    private val storage = FirebaseStorage.getInstance()
    private var storageRef = storage.getReference()

    fun uploadUserPhoto(uri: Uri,handler: StorageHandler){
        val filename = AuthManager.currentUser()!!.uid+".png"
        val uploadTask: UploadTask = storageRef.child(USER_PHOTO_PATH).child(filename).putFile(uri)
        uploadTask.addOnSuccessListener {
            val result = it.metadata!!.reference!!.downloadUrl;
            result.addOnSuccessListener {
                val imgUri = it.toString()
                handler.onSuccess(imgUri)
            }.addOnFailureListener { e->
                handler.onError(e)
            }
        }.addOnFailureListener{e->
            handler.onError(e)
        }
    }
}