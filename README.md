# Firebase Google Sign-In Demo (Android + Backend)

This project is a comprehensive, full-stack demonstration of how to implement Google Sign-In in an Android application with a secure backend verification system. It showcases the modern approach using Android's Credential Manager API and a Node.js (Express) backend to validate Google ID tokens.

The primary goal is to provide a clear, end-to-end example of a secure authentication flow, from the user tapping the "Sign in with Google" button on their device to the backend server verifying their identity and issuing a session token.

## Demo

<p>
  <img src="./demo/google-sign-in-demo.gif" height="400" alt="Firebase Google Sign-In Demo" />
</p>

## Project Structure

The repository is organized into two main parts:

*   `./android`: Contains the native Android application built with Kotlin. It handles the user interface, Google Sign-In flow via Credential Manager, and communication with the backend.
*   `./backend`: Contains the Node.js (Express) server built with TypeScript. It exposes an API endpoint to verify Google ID tokens and issue custom JWTs for session management.

## Features

*   **Android Client (using Credential Manager):**
    *   Modern, simplified Google Sign-In implementation.
    *   Login screen with a "Sign in with Google" button.
    *   Profile screen to display user information after successful login.
    *   Securely sends the Google ID token to the backend.
*   **Node.js Backend:**
    *   Verifies the integrity and authenticity of Google ID tokens.
    *   Issues custom JSON Web Tokens (JWTs) to the client for session management.
    *   Built with Express.js and TypeScript for a robust and scalable setup.

## Technologies Used

*   **Frontend (Android):**
    *   Kotlin
    *   Firebase Authentication
    *   Google Sign-In with Credential Manager API
*   **Backend:**
    *   Node.js with Express.js
    *   TypeScript
    *   Bun (runtime and package manager)
    *   JSON Web Tokens (JWT)
    *   Google Auth Library

## Getting Started

To run this demo, you need to set up a Firebase project, configure the backend, and then run the Android application.

### 1. Firebase Project Setup (Required for both Android and Backend)

Follow the detailed instructions in the [Android README](./android/README.md#1-firebase-project-setup) to:

1.  Create a Firebase project.
2.  Add your Android app to the project.
3.  Enable the Google Sign-In provider.
4.  Add your SHA-1 fingerprint.
5.  **Crucially, obtain your Web Client ID**, which is needed for both the Android app and the backend.

### 2. Backend Setup

1.  Navigate to the `backend` directory.
2.  Follow the instructions in the [backend README](./backend/README.md#2-backend-configuration) to install dependencies and create your `.env` file.
3.  Start the backend server:
    ```bash
    cd backend
    bun run dev
    ```
    The server will be running at `http://localhost:3000`.

### 3. Android App Setup

1.  Follow the instructions in the [Android README](./android/README.md#3-android-project-configuration) to add your Web Client ID to the project.
2.  **Set up a reverse proxy** to allow the app to communicate with your local backend:
    ```bash
    # Make sure your Android device is connected or emulator is running
    adb reverse tcp:3000 tcp:3000
    ```
3.  Build and run the app on your Android device or emulator.

## Detailed Documentation

For more detailed information on each part of the project, please refer to their respective README files:

*   **[Android README](./android/README.md)**
*   **[Backend README](./backend/README.md)**
