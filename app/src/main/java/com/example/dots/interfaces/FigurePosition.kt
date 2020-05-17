package com.example.dots.interfaces

import com.example.dots.figures.Circle

interface FigurePosition {
    val figures: MutableList<Figure>
    val circles: MutableList<Circle>
    val crosses: MutableList<Figure>
    val startFigure: Figure
    var lastFigure: Figure
    val finishFigure: Figure
    val ofx: Float
    val ofy: Float
}