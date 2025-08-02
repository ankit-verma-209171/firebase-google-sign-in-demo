# Firebase Google Sign-In Demo for Android

This project demonstrates how to implement Firebase Google Sign-In in an Android application using the new Credential Manager API. It also includes a backend verification process to ensure the integrity of the authentication tokens.

## Demo Overview

This demo showcases a simple yet complete authentication flow:

*   **Login Screen:** The app opens with a login screen featuring a "Sign in with Google" button.
*   **Google Sign-In:** Tapping the button initiates the Google Sign-In process using the Credential Manager API.
*   **Backend Verification:** Upon successful sign-in, the app receives an ID token from Google and sends it to a local backend server for verification.
*   **Profile Screen:** Once the token is verified, the user is navigated to a profile screen, which displays their email and a "Sign Out" button.
*   **Sign Out:** Tapping the "Sign Out" button clears the user's session and returns them to the login screen.

## Technologies Used

*   **Firebase Authentication:** For user authentication and management.
*   **Google Sign-In with Credential Manager:** The latest and recommended way to implement Google Sign-In on Android. You can find the core logic for this in [`GoogleAuthenticator.kt`](app/src/main/java/com/codeitsolo/firebasedemoapp/authenticator/GoogleAuthenticator.kt).
*   **Backend Token Verification:** A simple backend setup to demonstrate how to securely verify Google ID tokens. See the [backend README](../backend/README.md) for more details.

## Setup Instructions

### 1. Firebase Project Setup

1.  **Create a Firebase Project:** If you haven't already, create a new project in the [Firebase Console](https://console.firebase.google.com/).
2.  **Add an Android App:** Add a new Android app to your Firebase project. Follow the on-screen instructions to register your app's package name.
3.  **Download `google-services.json`:** Download the `google-services.json` configuration file and place it in the `app/` directory of your Android project.

### 2. Enable Google Sign-In in Firebase

1.  **Enable Google Provider:** In the [Firebase Console](https://console.firebase.google.com/), go to the **Authentication** section, then the **Sign-in method** tab, and enable the **Google** provider.
2.  **Add SHA-1 Fingerprint:** For Google Sign-In to work correctly, you must add the SHA-1 fingerprint of your signing certificate to your Firebase Android App configuration.
    *   Go to your **Project Settings** in the Firebase Console.
    *   In the **Your apps** card, select your Android app.
    *   Click **Add fingerprint** and enter your SHA-1 hash. You can get this by running `gradlew signingReport` in your Android Studio terminal.
3.  **Get Your Web Client ID:** When you enable Google Sign-In, a web client ID is automatically created and associated with your project. This ID is required for the backend server to verify the ID tokens sent from your Android app. You can find this ID in the Google provider settings within the Firebase Authentication section.
4.  **Download updated `google-services.json`:** After enabling Google Sign-In and adding your SHA-1 fingerprint, the `google-services.json` file is updated with the necessary OAuth client information. Download the latest version of this file from your Firebase project settings and replace the existing one in the `app/` directory.

### 3. Android Project Configuration

1.  **Add Server Client ID to `secrets.xml`:**
    *   Create a `secrets.xml` file in `app/src/main/res/values/` if it doesn't exist.
    *   Add your **Web application client ID** (obtained from the Firebase console) to the `secrets.xml` file as a string resource:

    ```xml
    <resources>
        <string name="server_client_id">YOUR_WEB_APP_CLIENT_ID</string>
    </resources>
    ```

## Running the Demo

### 1. Start the Backend Server

Before running the Android app, make sure your backend server is running locally. The server should be configured to handle the ID token verification.

### 2. Set Up Reverse Proxy

To allow the Android app to communicate with your local backend server, set up a reverse proxy using the Android Debug Bridge (adb):

```bash
./adb reverse tcp:3000 tcp:3000
```

This command forwards requests from the device's port 3000 to your computer's port 3000.

### 3. Run the Android App

Build and run the app on an Android device or emulator.

## Testing the Flow

1.  **Launch the App:** Open the app on your device.
2.  **Initiate Google Sign-In:** Tap the "Sign in with Google" button.
3.  **Select an Account:** The Credential Manager will prompt you to select a Google account.
4.  **Authentication:** After you select an account, the app will receive an ID token from Google.
5.  **Backend Verification:** The app will send the ID token to your local backend server for verification.
6.  **Result:** The server will verify the token and return a response to the app. The app will then display the result of the authentication and verification process.

## References

*   [Firebase Documentation: Authenticate with Google on Android](https://firebase.google.com/docs/auth/android/google-signin)
