# Firebase Google Sign-In Demo: Backend

This project is the backend server for the [Firebase Google Sign-In Demo for Android](../android/README.md). It's an Express.js server responsible for verifying Google ID tokens received from the Android client and issuing a custom session token.

This backend demonstrates a common pattern for applications that need to authenticate users on a client (like a mobile app) and then maintain a secure session with a custom backend.

## Technologies Used

*   **Node.js:** JavaScript runtime environment.
*   **Express.js:** Web application framework for Node.js.
*   **TypeScript:** Superset of JavaScript that adds static typing.
*   **Bun:** Used as the package manager and runtime.
*   **JSON Web Tokens (JWT):** For creating custom session tokens.
*   **Google Auth Library:** To verify Google ID tokens.

## Setup Instructions

### 1. Firebase Project Setup

Before running the backend, you need to set up a Firebase project and enable Google Sign-In.

1.  Follow the instructions in the **"Firebase Project Setup"** and **"Enable Google Sign-In in Firebase"** sections of the [Android app's README](../android/README.md).
2.  During this process, you will obtain a **Web Client ID**. This ID is crucial for the backend to verify the authenticity of Google ID tokens sent from the Android app.

### 2. Backend Configuration

1.  **Install dependencies:**
    Navigate to the `backend` directory and run:
    ```bash
    bun install
    ```

2.  **Create a `.env` file:**
    Create a `.env` file in the root of the `backend` project and add your Web Client ID and a secret for signing JWTs:
    ```
    WEB_CLIENT_ID="your-web-client-id-from-firebase"
    JWT_SECRET="your-super-secret-and-long-jwt-secret"
    ```
    Replace `"your-web-client-id-from-firebase"` with the ID you obtained from the Firebase console.

## Running the Server

### Development Mode

To run the server in development mode with hot-reloading:

```bash
bun run dev
```

The server will start on `http://localhost:3000`.

### Production Mode

To build and run the application in production mode:

```bash
bun run start
```

## Connecting the Android App

For the Android application to communicate with this local backend server, you need to set up a reverse proxy using the Android Debug Bridge (adb).

Run the following command in your terminal:

```bash
adb reverse tcp:3000 tcp:3000
```

This command forwards network requests from the Android device's port 3000 to your computer's port 3000, allowing the app to connect to your local server.

## API Endpoint

### `POST /api/auth/google`

This endpoint verifies a Google ID token received from the client and, if valid, returns a custom JWT and the user's email.

**Request Body:**

```json
{
  "idToken": "your-google-id-token"
}
```

**Response (on success):**

```json
{
  "accessToken": "your-custom-jwt-token",
  "email": "user.email@example.com"
}
```
