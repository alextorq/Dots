package com.example.dots.utils

import com.example.dots.LevelModel
import com.example.dots.figures.Circle
import com.example.dots.figures.Cross
import com.example.dots.figures.FinishCircle
import com.example.dots.figures.Rhombus
import com.example.dots.interfaces.Figure
import com.example.dots.interfaces.FigurePosition
import com.example.dots.interfaces.ScreenSizes

class CalculatePositionFigure(val model: LevelModel) {

    public fun calc(screenSizes: ScreenSizes): FigurePosition {

        val figures: MutableList<Figure> = mutableListOf<Figure>();
        val circles: MutableList<Circle> = mutableListOf<Circle>();
        val crosses: MutableList<Figure> = mutableListOf<Figure>();
        lateinit var startFigure: Figure;
        lateinit var lastFigure: Figure;
        lateinit var finishFigure: Figure;

        val amountRows: Float = model.amountFigure / model.amountFigureInRow
        val diameter: Float = model.circleRadius * 2F

        val onePercentWidth: Float = ((screenSizes.width).toFloat() / 100F)
        val circleRadiusPixelFormat: Float = Math.round((model.circleRadius * onePercentWidth).toFloat()).toFloat()

        val freeSpaceWidth: Float = (100F - (model.amountFigureInRow * diameter)).toFloat()
        val freeSpaceHeight: Float = (screenSizes.height.toFloat() - (amountRows * circleRadiusPixelFormat * 2))

        val ofx: Float = ((freeSpaceWidth / (model.amountFigureInRow + 1)) * onePercentWidth)
        val ofy: Float = (freeSpaceHeight / (amountRows + 1))


        for (i in 0 until model.amountFigure.toInt() ) {

            val countInRow: Int = (i % model.amountFigureInRow.toInt()) + 1
            val rowNumber: Int = Math.ceil((i / model.amountFigureInRow.toInt()).toDouble()).toInt() + 1
            val x: Float = (((countInRow *  (circleRadiusPixelFormat + ofx))) + ((countInRow - 1) * circleRadiusPixelFormat))
            val y: Float = (((rowNumber *  (circleRadiusPixelFormat + ofy))) + ((rowNumber - 1) * circleRadiusPixelFormat))

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
                    crosses.add(figure);
                }
                23 -> {
                    figure = FinishCircle(x, y, circleRadiusPixelFormat)
                    finishFigure = figure
                }
                else -> {
                    figure = Circle(x, y, circleRadiusPixelFormat)
                    circles.add(figure)
                }
            }


            figures.add(figure)
        }

        val result =  object: FigurePosition {
            override val figures = figures;
            override val circles = circles;
            override val crosses = crosses;
            override val startFigure = startFigure;
            override val lastFigure = lastFigure;
            override val finishFigure = finishFigure
            override val ofx = ofx;
            override val ofy = ofy;
        }

        return result;
    }

}