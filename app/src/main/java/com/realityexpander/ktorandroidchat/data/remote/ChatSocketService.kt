package com.realityexpander.ktorandroidchat.data.remote

import com.realityexpander.ktorandroidchat.domain.model.Message
import com.realityexpander.ktorandroidchat.util.Resource
import kotlinx.coroutines.flow.Flow

interface ChatSocketService {

    suspend fun initSession(
        username: String
    ): Resource<Unit>

    suspend fun sendMessage(message: String)

    fun observeMessages(): Flow<Message>

    suspend fun closeSession()

    companion object {
        //const val BASE_URL = "ws://192.168.0.186:8082" // local
        const val BASE_URL = "ws://82.180.173.232:8082" // remote

    }

    sealed class Endpoints(val url: String) {
        object ChatSocket: Endpoints("$BASE_URL/chat-socket")
    }
}