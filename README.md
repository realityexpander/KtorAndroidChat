# KtorAndroidChat
Android app for use with Ktor Chat backend

Android app for the this Ktor backend server: https://github.com/realityexpander/Server.ktor-chat

[<img src="https://user-images.githubusercontent.com/5157474/190579966-c9aa572b-46e4-4db9-a6fe-9ab49b06f269.png" width="400"/>](https://user-images.githubusercontent.com/5157474/190579966-c9aa572b-46e4-4db9-a6fe-9ab49b06f269.png)
[<img src="https://user-images.githubusercontent.com/5157474/190580415-a05d49e5-e55b-4a05-a029-b60e04cf1394.png" width="400"/>](https://user-images.githubusercontent.com/5157474/190580415-a05d49e5-e55b-4a05-a029-b60e04cf1394.png)




App features:
- Chat with other users in real-time
- DOES NOT use firebase
- Deployable to any Linux server including Virtual servers like Hostinger.com
- Night mode

# Technologies employed:

* Kotlin 
* MVVM
* Coroutines
* Compose for UI Layer
* MongoDB Database for persistence
* KotlinX Serialization
* Navigation Component
* Custom REST API built with Ktor
* Custom Websockets using Ktor Client for real-time communication
* Responds to custom HTTP & WebSocket endpoints with JSON
* Allows users to reconnect if accidentally disconnected
* Efficiently handles network errors
* Dependency injection with Dagger-Hilt
* Deployable Ktor server accessable from anywhere
* Custom automatic Deployment to Ubuntu servers via Gradle task (deploy)

Note : this repository only contains the android app part, Ktor API is in this repo:  https://github.com/realityexpander/Server.ktor-chat

To install the Apk:

1. Open this link on your Android device:
   https://github.com/realityexpander/KtorAndroidChat/blob/master/ktorchat_1.0.apk
2. Tap the "skewer" menu and tap the "download"

   [![](https://user-images.githubusercontent.com/5157474/147434050-57102a30-af32-46ed-a90b-d94e0c4a4f35.jpg)]()
3. Allow the file to download (DO NOT click "show details")
4. After the file is downloaded, click "OK" to install
5. Click "OK" to install
6. Click "OK" to launch

If you have developer options turned on, you may need to turn off "USB Debugging" if the "Waiting for debugger" dialog is displayed.
