package com.realityexpander.ktorandroidchat.presentation.chat

import com.realityexpander.ktorandroidchat.domain.model.Message

data class ChatState(
    val messages: List<Message> = emptyList(),
    val isLoading: Boolean = false
)
