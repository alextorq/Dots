package com.example.dots
import android.graphics.*
import kotlin.math.hypot


class Rhombus(private val startX: Float, private val startY: Float, val width: Float): Figure {
    private val paint = Paint();
    private val path = Path()
    private val color = Color.parseColor("#FF9900")
    private val cathet = (Math.sin(45.0) * width).toFloat()
    private val centerPoint: Point = Point(startX.toInt(), startY.toInt())

    init {
        paint.setColor(color)
        paint.setStrokeWidth(6F)
        val radius = 10.0f
        val corEffect = CornerPathEffect(radius)
        paint.setPathEffect(corEffect)
        path.moveTo(startX, startY + cathet)
        path.lineTo(startX + cathet, startY)
        path.lineTo(startX, startY - cathet)
        path.lineTo(startX - cathet, startY)
        path.lineTo(startX, startY + cathet)
        path.close()
    }

    override fun includeDot(x: Float, y: Float): Boolean {
        val x2: Float = x - startX
        val y2: Float = y - startY
        val hypotenuse: Float =  hypot(x2, y2)
        return cathet > hypotenuse;
    }

    override fun draw(canvas: Canvas, paint: Paint) {
        canvas.drawPath(path, this.paint)
    }

    override fun getCenterPoint(): Point {
        return centerPoint;
    }

}