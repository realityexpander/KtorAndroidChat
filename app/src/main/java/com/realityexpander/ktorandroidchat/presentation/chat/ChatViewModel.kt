package com.realityexpander.ktorandroidchat.presentation.chat

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.realityexpander.ktorandroidchat.data.remote.ChatSocketService
import com.realityexpander.ktorandroidchat.data.remote.MessageService
import com.realityexpander.ktorandroidchat.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val messageService: MessageService,
    private val chatSocketService: ChatSocketService,
    private val savedStateHandle: SavedStateHandle  // automatically injected via hiltViewModel(), includes the navArgs
) : ViewModel() {

    private val _messageText = mutableStateOf("")
    val messageText: State<String> = _messageText

    private val _state = mutableStateOf(ChatState())
    val state: State<ChatState> = _state

    private val _toastEvent = MutableSharedFlow<String>()
    val toastEvent = _toastEvent.asSharedFlow()

    fun connectToChat() {
        getAllMessages()

        // the `savedStateHandle` contains the arguments from the `navArguments`
        println("Connecting to chat: id=${savedStateHandle.get<String>("id")}") // not used, for demo only.

        savedStateHandle.get<String>("username")?.let { username ->
            viewModelScope.launch {
                val result = chatSocketService.initSession(username)

                when(result) {
                    is Resource.Success -> {
//                        chatSocketService.observeMessages()  // setup the flow for observing messages
//                            .onEach { message ->
//                                val newList =
//                                    state.value.messages.toMutableList().apply {
//                                        add(0, message)
//                                    }
//
//                                _state.value = state.value.copy(
//                                    messages = newList
//                                )
//                            }.launchIn(viewModelScope)

                        // Same as above.  The `onEach` is a flow, so needs a `collect` terminator.
                        viewModelScope.launch {
                            chatSocketService.observeMessages()  // setup the flow for observing messages
                                .onEach { message ->
                                    val newList =
                                        state.value.messages.toMutableList().apply {
                                            add(0, message)
                                        }

                                    _state.value = state.value.copy(
                                        messages = newList
                                    )
                                }.collect()
                        }
                    }
                    is Resource.Error -> {
                        _toastEvent.emit(result.message ?: "Unknown error")
                    }
                }
            }
        }
    }

    fun onMessageChange(message: String) {
        _messageText.value = message
    }

    fun disconnect() {
        viewModelScope.launch {
            chatSocketService.closeSession()
        }
    }

    fun getAllMessages() {
        viewModelScope.launch {
            _state.value = state.value.copy(isLoading = true)

            val result = messageService.getAllMessages()
            _state.value = state.value.copy(
                messages = result,
                isLoading = false
            )
        }
    }

    fun sendMessage() {
        viewModelScope.launch {
            if(messageText.value.isNotBlank()) {
                chatSocketService.sendMessage(messageText.value)

                // Clear the message text
                _messageText.value = ""
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        disconnect()
    }
}