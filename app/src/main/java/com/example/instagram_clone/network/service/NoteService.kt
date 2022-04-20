package com.example.instagramclone.network.service

import com.example.instagram_clone.model.FCMNote
import com.example.instagram_clone.model.FCMResp
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface NoteService {

    companion object {
        private const val SERVER_KEY =
            "AAAAqGw0exs:APA91bEpFZHGDt-2OfYtWzjcw9tEKQ9GBCVL0CwyJYXQT7uAvAl369cY8RFOWhNBVTFQPATSoD-u7CNpa25nmGbw4AsKdlw6NRzNR1G63N8ketbknn9_TGb0P2yc0VAXkqKtU59epMHl"
    }

    @Headers("Authorization:key=$SERVER_KEY")
    @POST("/fcm/send")
    fun sendNote(@Body fcmNote: FCMNote): Call<FCMResp>

}