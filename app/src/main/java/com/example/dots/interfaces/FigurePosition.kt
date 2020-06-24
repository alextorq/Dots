package com.example.dots.interfaces

import com.example.dots.figures.Circle
import com.example.dots.figures.Line
import com.example.dots.figures.RequiredCircle

interface FigurePosition {
    val figures: MutableList<Figure>
    val circles: MutableList<Circle>
    val crosses: MutableList<Figure>
    val requiredCircle: MutableList<RequiredCircle>
    val startFigure: Figure
    var lastFigure: Figure
    val finishFigure: Figure
    val ofx: Float
    val ofy: Float
    val lines: MutableList<Line>
}