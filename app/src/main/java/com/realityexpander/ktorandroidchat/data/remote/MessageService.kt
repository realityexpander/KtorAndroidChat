package com.realityexpander.ktorandroidchat.data.remote

import com.realityexpander.ktorandroidchat.domain.model.Message

interface MessageService {

    suspend fun getAllMessages(): List<Message>

    companion object {
        //const val BASE_URL = "http://192.168.0.186:8082" // local
        const val BASE_URL = "http://82.180.173.232:8082" // remote
    }

    sealed class Endpoints(val url: String) {
        object GetAllMessages: Endpoints("$BASE_URL/messages")
    }
}