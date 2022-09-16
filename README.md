# KtorAndroidChat
Android app for use with Ktor Chat backend

Android app for the this Ktor backend server: https://github.com/realityexpander/Server.ktor-chat

[<img src="https://user-images.githubusercontent.com/5157474/180717307-f9d8f72e-a289-4196-b4bc-520011a13f96.png" width="200"/>](https://user-images.githubusercontent.com/5157474/180717307-f9d8f72e-a289-4196-b4bc-520011a13f96.png)

App features:
- Chat with other users in real-time
- DOES NOT use firebase
- Deployable to any Linux server including Virtual servers like Hostinger.com
- Night mode

# Technologies employed:

* Kotlin 
* MVVM
* Coroutines
* Custom REST API built with Ktor
* Websockets using Ktor Client for real-time communication
* Responds to custom HTTP & HTTPS endpoints with JSON
* Allows users to reconnect if accidentally disconnected
* Efficiently handles network errors
* Dependency injection with Dagger-Hilt
* Deployable Ktor server accessable from anywhere
* Deployable to Ubuntu servers

Note : this repository only contains the android app part, Ktor API is in this repo:  https://github.com/realityexpander/Server.ktor-chat

To install the Apk:

1. Open this link on your Android device:
   https://github.com/realityexpander/GuessASketch/blob/master/ktorchat_1.0.apk
2. Tap the "skewer" menu and tap the "download"

   [![](https://user-images.githubusercontent.com/5157474/147434050-57102a30-af32-46ed-a90b-d94e0c4a4f35.jpg)]()
3. Allow the file to download (DO NOT click "show details")
4. After the file is downloaded, click "OK" to install
5. Click "OK" to install
6. Click "OK" to launch

If you have developer options turned on, you may need to turn off "USB Debugging" if the "Waiting for debugger" dialog is displayed.
