package com.example.dots

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import kotlin.math.hypot

class Circle(val cx: Float, val cy: Float, private val radius: Float): Figure {

    private var backgroundColor: Int = Color.parseColor("#151515")
    private val borderColor: Int = Color.parseColor("#FFFFFF")
    private val paint = Paint()
    private var borderWidth: Int = 3;
    private val centerPoint: Point = Point(cx.toInt(), cy.toInt())

    override fun getCenterPoint(): Point {
        return centerPoint
    }

    init {
        paint.alpha = 255
        paint.isAntiAlias = true
    }

    override fun draw(canvas: Canvas, paint: Paint) {
        this.paint.color = borderColor
        canvas.drawCircle(cx, cy, radius, this.paint)
        this.paint.color = backgroundColor
        canvas.drawCircle(cx, cy, radius - borderWidth, this.paint)
    }

    override fun includeDot(x: Float, y: Float): Boolean {
        val x2: Float = x -cx
        val y2: Float = y - cy
        val hypotenuse: Float =  hypot(x2, y2)
        return radius > hypotenuse;
    }

    fun setActive() {
        borderWidth = 10;
    }

    fun setNormal() {
        borderWidth = 2;
    }
}