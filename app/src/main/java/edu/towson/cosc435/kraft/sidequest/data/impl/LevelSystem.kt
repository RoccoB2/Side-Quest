package edu.towson.cosc435.kraft.sidequest.data.impl

import edu.towson.cosc435.kraft.sidequest.DifficultyEnum
import edu.towson.cosc435.kraft.sidequest.data.ILevelSystem
import edu.towson.cosc435.kraft.sidequest.data.model.Level
import kotlin.math.pow

class LevelSystem: ILevelSystem {
    var _level: Level = Level(1, 0, 5)
    var expMultplier = 1
    override fun getLevelObj(): Level{
        return _level
    }
    override fun getLevel(): Long {
        return _level.level
    }

    override fun getCurrentExp(): Long {
        return _level.currentExp
    }

    override fun getExpTillLevelUp(): Long {
        return _level.expTillLevelUp
    }

    override fun addExp(difficulty: DifficultyEnum) {
        val level: Double = _level.level.toDouble()
        when(difficulty){
            DifficultyEnum.easy -> {
                _level.currentExp += (3 * ((level).pow(0.7)) * expMultplier).toLong()
                checkLevelUp()
            }

            DifficultyEnum.medium -> {
                _level.currentExp += (5 * ((level).pow(0.7)) * expMultplier).toLong()
                checkLevelUp()
            }

            DifficultyEnum.hard -> {
                _level.currentExp += (9 * ((level).pow(0.7)) * expMultplier).toLong()
                checkLevelUp()
            }

            else -> { // challenges go here
                _level.currentExp = _level.currentExp
                checkLevelUp()
            }
        }
    }

    override fun checkLevelUp() {
        if(_level.currentExp >= _level.expTillLevelUp){
            _level.level += 1
            _level.currentExp = _level.currentExp - _level.expTillLevelUp
            calculateExpForNextLevel()
            checkLevelUp()
        }
    }

    override fun calculateExpForNextLevel() {
        val level: Double = _level.level.toDouble()
        _level.expTillLevelUp = (2 * ((level).pow(1.7)) - 2).toLong()
    }

}