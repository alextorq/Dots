package com.example.dots.figures

import android.graphics.*
import com.example.dots.interfaces.Figure
import kotlin.math.hypot

class Cross(private val startX: Float, private val startY: Float, val width: Float):
    Figure {
    private val paint = Paint();
    private val path = Path()
    private val color = Color.parseColor("#FFFFFF")
    private val cathet = (Math.sin(45.0) * width).toFloat()
    private val centerPoint: Point = Point(startX.toInt(), startY.toInt())

    init {
        paint.style = Paint.Style.STROKE
        paint.setStrokeWidth(4F)
        paint.color = color
        paint.alpha = 255
        paint.isAntiAlias = true


        path.moveTo(startX + cathet, startY - cathet)
        path.lineTo(startX - cathet, startY + cathet)
        path.moveTo(startX - cathet, startY - cathet)
        path.lineTo(startX + cathet, startY + cathet)

        path.close()
    }

    override fun draw(canvas: Canvas, paint: Paint) {
        canvas.drawPath(path, this.paint)
    }

    override fun getCenterPoint(): Point {
        return centerPoint;
    }

    override fun includeDot(point: Point): Boolean {
        val x2: Float = point.x -startX
        val y2: Float = point.y - startY
        val hypotenuse: Float =  hypot(x2, y2)
        return cathet > hypotenuse;
    }

}