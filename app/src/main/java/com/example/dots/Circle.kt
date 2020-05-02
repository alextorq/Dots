package com.example.dots

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import kotlin.math.hypot
import kotlin.math.sqrt

class Circle(val cx: Float, val cy: Float, private val radius: Float): Figure {

    public var color: String = "#151515"

    override fun draw(canvas: Canvas, paint: Paint) {
        canvas.drawCircle(cx, cy, radius, paint)
        val newPaint: Paint = Paint()
        newPaint.color = Color.parseColor(color)
        canvas.drawCircle(cx, cy, radius - 4, newPaint)
    }

    fun includeDot(x: Float, y: Float): Boolean {
        val x2: Float = x -cx
        val y2: Float = y - cy
        val hypotenuse: Float =  hypot(x2, y2)
        return radius > hypotenuse;
    }
}