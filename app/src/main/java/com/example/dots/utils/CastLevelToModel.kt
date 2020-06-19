package com.example.dots.utils
import com.example.dots.LevelModel
import com.example.dots.levelMap
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


data class FigureDescription(val type: String? = "") {
}

//data class Line


//fun cast(level: levelMap): LevelModel {
//    val amountFigure: Float = level["amountFigure"] as Float
//    val amountFigureInRow: Float = level["amountFigureInRow"] as Float
//    val circleRadius: Float = level["circleRadius"] as Float
//    val amountSteps: Int = level["amountSteps"] as Int
//    val currentStep: Int = level["currentStep"] as Int
//    val figures: String = level["figures"] as String
//    val lines: String = level["lines"] as String
//
//    val listPersonType = object : TypeToken<List<FigureDescription>>() {}.type
//    var figuresClassArray: List<FigureDescription> = Gson().fromJson(figures, listPersonType)
//
//
//
//    return LevelModel(amountFigure, amountFigureInRow, circleRadius, amountSteps, figuresClassArray, lines, currentStep)
//}
