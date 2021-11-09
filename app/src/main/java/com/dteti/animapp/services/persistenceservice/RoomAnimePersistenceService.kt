package com.dteti.animapp.services.persistenceservice

import android.content.Context
import androidx.room.Room
import com.dteti.animapp.services.persistenceservice.room.AnimAppDatabase
import com.dteti.animapp.services.persistenceservice.room.entities.Anime
import kotlinx.coroutines.*

class RoomAnimePersistenceService (
    private val scope: CoroutineScope,
    private val context: Context): AnimePersistenceService {

    private lateinit var database: AnimAppDatabase

    init {
        scope.launch(Dispatchers.IO) {
            database = Room.databaseBuilder(
                context,
                AnimAppDatabase::class.java, "animapp-db"
            ).fallbackToDestructiveMigration().build()
        }
    }

    override suspend fun readAllAnimes(): List<Anime> {
        return database.animeDao().readAll()
    }

    override suspend fun readAnimeDetailsById(id: Int): Anime? {
        return database.animeDao().readById(id)
    }

    override suspend fun persistAnime(anime: Anime) {
        database.animeDao().insert(anime)
    }

    override suspend fun deleteAnime(anime: Anime) {
        database.animeDao().delete(anime)
    }

}