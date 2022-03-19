package com.quanlt.eventfilter.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.quanlt.eventfilter.R
import com.quanlt.eventfilter.data.remote.CloudService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(ActivityRetainedComponent::class)
@Module
class ServiceModule {
    @Provides
    @ActivityRetainedScoped
    fun provideVinCartServiceBuilder(
        @ApplicationContext context: Context,
        gson: Gson
    ): Retrofit.Builder {

        val gson = GsonBuilder()
            .create()

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson));
    }

    @Provides
    fun provideGson() = GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
        .create()

    @Provides
    fun provideCloudService(
        @ApplicationContext context: Context,
        builder: Retrofit.Builder
    ): CloudService {
        return builder.baseUrl(context.getString(R.string.endpoint_cloud_service))
            .build()
            .create(CloudService::class.java);
    }
}
