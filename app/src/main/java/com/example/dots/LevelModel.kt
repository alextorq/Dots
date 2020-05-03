package com.example.dots

import android.graphics.Color

class LevelModel(screenSizes: ScreenSizes) {
    public val amountFigure: Int = 23;
    public val amountFigureInRow: Int = 4;
    public val circleRadius: Int = 4
    public val amountSteps: Int = 6

    public val onePercent: Int = (screenSizes.width / 100)
    public val circleRadiusPixelFormat: Float = (amountFigureInRow * (screenSizes.width / 100)).toFloat()

    public val figures: MutableList<Figure> = mutableListOf<Figure>();
    public val circles: MutableList<Circle> = mutableListOf<Circle>();
    public lateinit var startFigure: Figure;
    public lateinit var lastFigure: Figure;

    init {
        for (i in 0..amountFigure) {
            val countInRow: Int = i % amountFigureInRow
            val rowNumber: Double = Math.ceil((i / amountFigureInRow).toDouble())
            val x: Float = (offsetX + (dx + circleRadiusPixelFormat) * countInRow).toFloat()
            val y: Float = (offsetY + (dy + circleRadiusPixelFormat) * rowNumber).toFloat()

            if (i == 0) {
                startFigure = Rhombus(x, y , circleRadiusPixelFormat);
                lastFigure = Rhombus(x, y , circleRadiusPixelFormat);
            }else {
                circles.add(Circle(x, y, circleRadiusPixelFormat))
            }
        }
    }
}