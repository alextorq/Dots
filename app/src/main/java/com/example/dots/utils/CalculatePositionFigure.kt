package com.example.dots.utils

import com.example.dots.LevelModel
import com.example.dots.figures.*
import com.example.dots.interfaces.Figure
import com.example.dots.interfaces.FigurePosition
import com.example.dots.interfaces.ScreenSizes

class CalculatePositionFigure(val model: LevelModel) {

    public fun calc(screenSizes: ScreenSizes): FigurePosition {

        val figures: MutableList<Figure> = mutableListOf<Figure>();
        val circles: MutableList<Circle> = mutableListOf<Circle>();
        val crosses: MutableList<Figure> = mutableListOf<Figure>();
        val requiredCircle: MutableList<RequiredCircle> = mutableListOf<RequiredCircle>();

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
            val type: String? = model.figures[i].type

            var figure: Figure? = null;

            when (type) {
                "start" -> {
                    val rhombus = Rhombus(x, y, circleRadiusPixelFormat, i)
                    figure = rhombus
                    startFigure = rhombus
                    lastFigure = rhombus
                }
                "requiredDot" -> {
                    figure = RequiredCircle(x, y, circleRadiusPixelFormat, i)
                    requiredCircle.add(figure)
                }


                "cross" -> {
                    val finish: Figure = Cross(x, y, circleRadiusPixelFormat, i)
                    figure = finish
                    crosses.add(figure);
                }
                "finish" -> {
                    figure = FinishCircle(x, y, circleRadiusPixelFormat, i)
                    finishFigure = figure
                }
                else -> {
                    figure = Circle(x, y, circleRadiusPixelFormat, i)
                    circles.add(figure)
                }
            }


            figures.add(figure)
        }

        val lines: MutableList<Line> = mutableListOf<Line>()
        model.lines.forEach { it ->
            val startPointIndex = it[0];
            val stopPointIndex = it[1];
            figures[startPointIndex].setActive(null)
            figures[stopPointIndex].setActive(null)
            lines.add(Line(figures[startPointIndex].getCenterPoint(), figures[stopPointIndex].getCenterPoint()))

            lastFigure = figures[stopPointIndex]
        }


        val result =  object: FigurePosition {
            override val figures = figures;
            override val circles = circles;
            override val crosses = crosses;
            override val requiredCircle = requiredCircle;
            override val startFigure = startFigure;
            override var lastFigure = lastFigure;
            override val finishFigure = finishFigure
            override val ofx = ofx;
            override val ofy = ofy;
            override val lines = lines;
        }

        return result;
    }

}

