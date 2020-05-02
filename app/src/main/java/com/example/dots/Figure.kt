package com.example.dots

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point


interface Figure {
    fun draw(canvas: Canvas, paint: Paint)

    fun includeDot(x: Float, y: Float): Boolean

    fun getCenterPoint() :Point
}