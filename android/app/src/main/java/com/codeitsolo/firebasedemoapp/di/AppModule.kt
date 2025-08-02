package com.codeitsolo.firebasedemoapp.di

import android.content.Context
import android.content.SharedPreferences
import com.codeitsolo.firebasedemoapp.authenticator.Authenticator
import com.codeitsolo.firebasedemoapp.authenticator.GoogleAuthenticator
import com.google.firebase.auth.FirebaseAuth
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun bindAuthenticator(googleAuthenticator: GoogleAuthenticator): Authenticator

    companion object {
        @Provides
        @Singleton
        fun provideFirebaseAuth(): FirebaseAuth {
            return FirebaseAuth.getInstance()
        }

        @Provides
        @Singleton
        fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
            return context.getSharedPreferences("firebase_demo_app_prefs", Context.MODE_PRIVATE)
        }
    }
}