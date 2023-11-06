package edu.towson.cosc435.kraft.sidequest.data.impl

import edu.towson.cosc435.kraft.sidequest.DifficultyEnum
import edu.towson.cosc435.kraft.sidequest.data.ILevelSystem
import edu.towson.cosc435.kraft.sidequest.data.model.Level
import java.lang.Math

class LevelSystem: ILevelSystem {
    var _level: Level = Level(1, 0, 3)
    var expMultplier = 1
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
                _level.currentExp += (Math.pow(3*(level), 0.7) * expMultplier).toLong()
                checkLevelUp()
            }

            DifficultyEnum.medium -> {
                _level.currentExp += ((Math.pow(5*(level), 0.7) * expMultplier).toLong() * expMultplier)
                checkLevelUp()
            }

            DifficultyEnum.hard -> {
                _level.currentExp += ((Math.pow(9*(level), 0.7) * expMultplier).toLong() * expMultplier)
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
        _level.expTillLevelUp = (Math.pow(2*(level), 1.7) - 2).toLong()
    }

}