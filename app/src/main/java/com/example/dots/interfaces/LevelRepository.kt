package com.example.dots.interfaces

import com.example.dots.LevelModel

interface LevelRepository {

    fun getAllLevels(initial: Boolean = false) : MutableList<LevelModel>

    fun saveLevel(level: LevelModel, name: Int): LevelModel

}