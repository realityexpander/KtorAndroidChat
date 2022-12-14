package com.realityexpander.ktorandroidchat

import android.content.Intent
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.realityexpander.ktorandroidchat.presentation.chat.ChatScreen
import com.realityexpander.ktorandroidchat.presentation.username.UsernameScreen
import dagger.hilt.android.AndroidEntryPoint
import java.io.Serializable

// From web console:
// let ws1 = new WebSocket("ws://localhost:8082/chat-socket?username=phil")
// ws1.send(JSON.stringify({type: "message", data: "Hello World"}))

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            // for testing, terminal:
            // Put App in the background (Home button)
            //   adb shell am kill com.realityexpander.ktorandroidchat
            // Open App again
            android.os.Debug.waitForDebugger()

            println("savedInstanceState[\"username\"] = ${savedInstanceState.getString("username")}")

            // set the classLoader for the serializable (for Tiramisu, not needed for parcelable)
            savedInstanceState.classLoader.loadClass("com.realityexpander.ktorandroidchat.ChatMessage2")

            println("savedInstanceState[\"ChatMessageParcelable\"] = ${savedInstanceState.parcelable<ChatMessage>("ChatMessageParcelable")}")
            println("savedInstanceState[\"ChatMessage2Serializable\"] = ${savedInstanceState.serializable<ChatMessage2>("ChatMessage2Serializable")}")
        }

        setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = "username_screen"
            ) {
                composable("username_screen") {
                    UsernameScreen(onNavigate = navController::navigate)
                }
                composable(
                    route = "chat_screen/{username}/{id}",  // note: `id` is not used. Just for demo purposes.
                    arguments = listOf(
                        navArgument(name = "username") {
                            type = NavType.StringType
                            nullable = true
                        },
                        navArgument(name = "id") {
                            type = NavType.IntType
                            //nullable = true
                        }
                    )
                ) { navBackStackEntry ->
                    val username = navBackStackEntry.arguments?.getString("username")
                    val id = navBackStackEntry.arguments?.getInt("id")

                    // note: the navArguments are passed as a bundle to the viewModel via the SavedStateHandle.
                    // We are also passing the same arguments to the ChatScreen composable (necessary?)

                    ChatScreen(username = username, id = id)
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        println("onSaveInstanceState")

        outState.putString("username", "username")
        outState.putParcelable(
            "ChatMessageParcelable",
            ChatMessage("the username", "the message", System.currentTimeMillis())
        )
        outState.putSerializable(
            "ChatMessage2Serializable",
            ChatMessage2("A username", "A message", System.currentTimeMillis())
        )
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        println("onRestoreInstanceState")

        savedInstanceState.keySet().forEach {
            println("key: $it")
        }
    }
}

class ChatMessage constructor(
    val username: String?,
    val message: String?,
    val timestamp: Long
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readLong()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(username)
        parcel.writeString(message)
        parcel.writeLong(timestamp)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ChatMessage> {
        override fun createFromParcel(parcel: Parcel): ChatMessage {
            return ChatMessage(parcel)
        }

        override fun newArray(size: Int): Array<ChatMessage?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "ChatMessage(username=$username, message=$message, timestamp=$timestamp)"
    }
}

data class ChatMessage2(
    val username: String,
    val message: String,
    val timestamp: Long
) : Serializable


//// Helper function for bundles/intents for parcelables and serializables

inline fun <reified T : Serializable> Bundle.serializable(key: String): T? =
    when {
        SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializable(key, T::class.java)
        else -> @Suppress("DEPRECATION") getSerializable(key) as? T
    }

inline fun <reified T : Serializable> Intent.serializable(key: String): T? =
    when {
        SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializableExtra(key, T::class.java)
        else -> @Suppress("DEPRECATION") getSerializableExtra(key) as? T
    }

inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? =
    when {
        SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getParcelable(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelable(key) as? T
    }

inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? =
    when {
        SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getParcelableExtra(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
    }

inline fun <reified T : Parcelable> Bundle.parcelableArrayList(key: String): ArrayList<T>? =
    when {
        SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getParcelableArrayList(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelableArrayList(key)
    }

inline fun <reified T : Parcelable> Intent.parcelableArrayList(key: String): ArrayList<T>? =
    when {
        SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getParcelableArrayListExtra(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelableArrayListExtra(key)
    }
