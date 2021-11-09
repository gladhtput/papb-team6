package com.dteti.animapp.common.dependencyinjection

import android.content.Context
import com.dteti.animapp.services.animeapiservice.AnimeApiService
import com.dteti.animapp.services.animeapiservice.JikanAnimeApiService
import com.dteti.animapp.services.persistenceservice.AnimePersistenceService
import com.dteti.animapp.services.persistenceservice.RoomAnimePersistenceService
import com.dteti.animapp.usecases.AnimeUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideAnimeService(
        @ApplicationContext context: Context
    ): AnimeApiService {
        return JikanAnimeApiService(context)
    }

    @DelicateCoroutinesApi
    @Singleton
    @Provides
    fun provideAnimePersistenceService(
        @ApplicationContext context: Context
    ): AnimePersistenceService {
        return RoomAnimePersistenceService(GlobalScope, context)
    }
}