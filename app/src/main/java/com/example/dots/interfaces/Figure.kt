package com.example.dots.interfaces

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point


interface Figure {
    fun draw(canvas: Canvas, paint: Paint)

    fun includeDot(point: Point): Boolean

    fun getCenterPoint() :Point
}