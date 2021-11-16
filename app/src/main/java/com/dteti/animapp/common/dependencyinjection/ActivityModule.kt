package com.dteti.animapp.common.dependencyinjection

import com.dteti.animapp.services.animeapiservice.AnimeApiService
import com.dteti.animapp.services.persistenceservice.AnimePersistenceService
import com.dteti.animapp.services.persistenceservice.RoomAnimePersistenceService
import com.dteti.animapp.usecases.AnimeUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {
    @ActivityScoped
    @Provides
    fun provideAnimeUseCases(
        animeApiService: AnimeApiService,
        animePersistenceService: AnimePersistenceService
    ): AnimeUseCases {
        return AnimeUseCases(animeApiService, animePersistenceService)
    }
}