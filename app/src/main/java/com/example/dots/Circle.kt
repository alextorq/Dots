package com.example.dots

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class Circle(private val x: Float, private val y: Float, private val radius: Float): Figure {
    override fun draw(canvas: Canvas, paint: Paint) {
        canvas.drawCircle(x, y, radius, paint)
        val newPaint: Paint = Paint()
        newPaint.color = Color.parseColor("#151515")
        canvas.drawCircle(x, y, radius - 4, newPaint)
    }
}