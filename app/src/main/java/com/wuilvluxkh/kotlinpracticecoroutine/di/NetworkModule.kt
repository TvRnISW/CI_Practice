package com.wuilvluxkh.kotlinpracticecoroutine.di

import com.wuilvluxkh.kotlinpracticecoroutine.api.UserAPI
import com.wuilvluxkh.kotlinpracticecoroutine.utils.Constant.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofit():Retrofit{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }

    @Singleton
    @Provides
    fun providesUserAPI(retrofit: Retrofit):UserAPI{
        return retrofit.create(UserAPI::class.java)
    }

}