package com.example.dots.interfaces

import com.example.dots.figures.Circle

interface FigurePosition {
    val figures: MutableList<Figure>
    val circles: MutableList<Circle>
    val startFigure: Figure
    val lastFigure: Figure
    val ofx: Float
    val ofy: Float
}