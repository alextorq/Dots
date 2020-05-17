package com.example.dots.figures

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import com.example.dots.interfaces.Figure
import kotlin.math.hypot

class FinishCircle(val cx: Float, val cy: Float, private val radius: Float):
    Figure {
    private val borderColor: Int = Color.parseColor("#FF9900")
    private val paint = Paint()
    private val centerPoint: Point = Point(cx.toInt(), cy.toInt())
    /*Поправка на криворукость*/
    private val fingerCorrect = 20

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
    }

    override fun includeDot(point: Point): Boolean {
        val x2: Float = point.x -cx
        val y2: Float = point.y - cy
        val hypotenuse: Float =  hypot(x2, y2)
        return (radius + fingerCorrect) > hypotenuse;
    }

    override fun includeLine(p1: Point, p2: Point): Boolean {

        val x01 = p1.x - centerPoint.x;
        val y01 = p1.y - centerPoint.y;

        val x02 = p2.x - centerPoint.x;
        val y02 = p2.y - centerPoint.y;

        val dx = x02 - x01;
        val dy = y02 - y01;

        val a = dx*dx + dy*dy;
        val b = 2.0f * (x01 * dx + y01 * dy);
        val c = x01*x01 + y01*y01 - radius*radius;

        if(-b < 0) {
            return (c < 0);
        }
        if(-b < (2.0f * a)){
            return (4.0f * a*c - b*b < 0);
        }
        return (a + b + c < 0);
    }

    fun setActive() {
//        borderWidth = 10;
    }

    fun setNormal() {
//        borderWidth = 2;
    }
}