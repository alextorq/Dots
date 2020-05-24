package com.example.dots.figures

import android.graphics.*
import android.os.Handler
import android.view.View
import com.example.dots.interfaces.Figure
import com.example.dots.utils.FiguresParams
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
        paint.setStrokeWidth(FiguresParams.CrossStroke)
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

    override fun includeLine(p1: Point, p2: Point): Boolean {

        val x01 = p1.x - centerPoint.x;
        val y01 = p1.y - centerPoint.y;

        val x02 = p2.x - centerPoint.x;
        val y02 = p2.y - centerPoint.y;

        val dx = x02 - x01;
        val dy = y02 - y01;

        val a = dx*dx + dy*dy;
        val b = 2.0f * (x01 * dx + y01 * dy);
        val c = x01*x01 + y01*y01 - width*width;

        if(-b < 0) {
            return (c < 0);
        }
        if(-b < (2.0f * a)){
            return (4.0f * a*c - b*b < 0);
        }
        return (a + b + c < 0);
    }

    override fun includeDot(point: Point): Boolean {
        val x2: Float = point.x -startX
        val y2: Float = point.y - startY
        val hypotenuse: Float =  hypot(x2, y2)
        return cathet > hypotenuse;
    }

    override fun setActive(context: View) {

    }

}