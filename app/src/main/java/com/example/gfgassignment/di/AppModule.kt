package com.example.gfgassignment.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.gfgassignment.Database.FeedDatabase
import com.example.gfgassignment.network.ApiService
import com.example.gfgassignment.network.NewsRepository
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    // Provides ApiService instance for API communication
    @Singleton
    @Provides
    fun provideApi(): ApiService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.rss2json.com/")
            .build()
            .create(ApiService::class.java)
    }

    // Provides FeedDatabase instance for database operations
    @Provides
    @Singleton
    fun provideFeedDatabase(@ApplicationContext app: Context): FeedDatabase {
        return Room.databaseBuilder(app, FeedDatabase::class.java, "Feed")
            .build()
    }
}
