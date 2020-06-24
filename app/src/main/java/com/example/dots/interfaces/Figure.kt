package com.example.dots.interfaces

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import android.view.View


interface Figure {
    fun draw(canvas: Canvas, paint: Paint)

    fun includeDot(point: Point): Boolean

    fun getCenterPoint() :Point

    fun includeLine(p1: Point, p2: Point): Boolean

    fun setActive(context: View?)

    val playingField: Int

}