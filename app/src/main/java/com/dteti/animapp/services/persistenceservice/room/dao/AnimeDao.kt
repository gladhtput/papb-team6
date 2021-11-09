package com.dteti.animapp.services.persistenceservice.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.dteti.animapp.services.persistenceservice.room.entities.Anime

@Dao
interface AnimeDao {
    @Query("SELECT * FROM anime")
    fun readAll(): List<Anime>

    @Query("SELECT * FROM anime WHERE id=:id")
    fun readById(id: Int): Anime?

    @Insert
    fun insert(anime: Anime)

    @Delete
    fun delete(anime: Anime)
}