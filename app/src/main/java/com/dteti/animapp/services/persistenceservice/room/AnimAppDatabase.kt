package com.dteti.animapp.services.persistenceservice.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dteti.animapp.services.persistenceservice.room.dao.AnimeDao
import com.dteti.animapp.services.persistenceservice.room.entities.Anime

@Database(entities = [Anime::class], version = 2)
abstract class AnimAppDatabase: RoomDatabase() {
    abstract fun animeDao(): AnimeDao
}