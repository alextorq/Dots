package com.example.dots

import com.example.dots.figures.Circle
import com.example.dots.figures.Cross
import com.example.dots.figures.FinishCircle
import com.example.dots.figures.Rhombus
import com.example.dots.interfaces.Figure
import com.example.dots.interfaces.ScreenSizes

class LevelModel(screenSizes: ScreenSizes) {
    public val amountFigure: Float = 24F;
    public val amountFigureInRow: Float = 4F;
    public val circleRadius: Float = 4F

    public val amountRows: Float = amountFigure / amountFigureInRow
    public val amountSteps: Int = 6
    public val diameter: Float = circleRadius * 2F
    public val yOffset = screenSizes.yOffset

    public val onePercentWidth: Float = ((screenSizes.width).toFloat() / 100F)
    public val onePercentHeight: Int = (screenSizes.height / 100)
    public val circleRadiusPixelFormat: Float = Math.round((circleRadius * onePercentWidth).toFloat()).toFloat()
    public val circleRadiusHeight: Float = (circleRadiusPixelFormat * 2) / onePercentHeight

    public val freeSpaceWidth: Float = (100F - (amountFigureInRow * diameter)).toFloat()

    public val ofx: Float = ((freeSpaceWidth / (amountFigureInRow + 1)) * onePercentWidth)

    public val ofy = (((100 - (amountRows * circleRadiusHeight))
            / (amountRows + 1)) * onePercentHeight)

    public val figures: MutableList<Figure> = mutableListOf<Figure>();
    public val circles: MutableList<Circle> = mutableListOf<Circle>();
    public lateinit var startFigure: Figure;
    public lateinit var lastFigure: Figure;


    init {
        for (i in 0 until amountFigure.toInt() ) {

            val countInRow: Int = (i % amountFigureInRow.toInt()) + 1
            val rowNumber: Int = Math.ceil((i / amountFigureInRow.toInt()).toDouble()).toInt()
            val x: Float = (((countInRow *  (circleRadiusPixelFormat + ofx))) + ((countInRow - 1) * circleRadiusPixelFormat))
            val y: Float = ((ofy + ((ofy + circleRadiusPixelFormat)) * rowNumber)).toFloat()

            var figure: Figure? = null;

            when (i) {
                0 -> {
                    val rhombus = Rhombus(
                        x,
                        y,
                        circleRadiusPixelFormat
                    )
                    figure = rhombus
                    startFigure = rhombus
                    lastFigure = rhombus
                }
                10 -> {
                    val finish: Figure =
                        Cross(x, y, circleRadiusPixelFormat)
                    figure = finish
                }
                23 -> {
                    figure = FinishCircle(x, y, circleRadiusPixelFormat)
                }
                else -> {
                    figure = Circle(x, y, circleRadiusPixelFormat)
                    circles.add(figure)
                }
            }


            figures.add(figure)
        }
    }
}