package com.example.dots.figres
import android.graphics.*
import com.example.dots.Figure
import kotlin.math.hypot


class Rhombus(private val startX: Float, private val startY: Float, val width: Float):
    Figure {
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

    override fun includeDot(point: Point): Boolean {
        val x2: Float = point.x -startX
        val y2: Float = point.y - startY
        val hypotenuse: Float =  hypot(x2, y2)
        return cathet > hypotenuse;
    }

    fun includeLine(center: Point, radius: Float, p1: Point, p2: Point): Boolean {

        val x01 = p1.x - center.x;
        val y01 = p1.y - center.y;

        val x02 = p2.x - center.x;
        val y02 = p2.y - center.y;

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

    override fun draw(canvas: Canvas, paint: Paint) {
        canvas.drawPath(path, this.paint)
    }

    override fun getCenterPoint(): Point {
        return centerPoint;
    }

}