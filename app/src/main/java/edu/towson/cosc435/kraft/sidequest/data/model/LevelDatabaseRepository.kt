package edu.towson.cosc435.kraft.sidequest.data.model

import android.app.Application
import androidx.room.Room
import edu.towson.cosc435.kraft.sidequest.data.ILevelSystem
import edu.towson.cosc435.kraft.sidequest.data.LevelDatabase

// LevelDatabaseRepository connects the frontend to the rooms database
// takes in an instance of the application to provide the context required by the rooms database api
// implements the ILevelSystem interface to provide a framework for needed functions
class LevelDatabaseRepository(app: Application): ILevelSystem {

    private val db: LevelDatabase // db will hold an instance of the level database

    // init block executes the code below when initialized
    init {
        db = Room.databaseBuilder(app, LevelDatabase::class.java, "level.db").build() // adds the database to the db variable
    }

    // returns the level of the user held in the rooms database level table
    override suspend fun getLevel(): Level {
        return db.levelDao().getLevel() // returns level
    }

    // adds exp to the users currentExp variable in the level table
    override suspend fun addExp(level : Level) {
        db.levelDao().updateLevel(level) // passes the new level data object to the updateLevel class
    }

    // adds level to the users level table in the rooms database
    override suspend fun addLevel(level : Level) {
        db.levelDao().addLevel(level) // adds level
    }



}