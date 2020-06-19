package com.example.dots

import com.example.dots.utils.FigureDescription


data class LevelModel(val amountFigure: Float = 36F,
                      val amountFigureInRow: Float = 6F,
                      val circleRadius: Float = 4F,
                      val amountSteps: Int = 6,
                      var figures: List<FigureDescription>,
                      var lines: MutableList<List<Int>>,
                      var currentStep: Int = 0) {
}