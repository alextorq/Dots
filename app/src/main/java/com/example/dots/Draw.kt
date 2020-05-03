package com.example.dots

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.util.Log
import android.view.View

class Draw(parentContext: Context, private val actionBarOffset: Int): View(parentContext) {
    private val radius: Float = 30F;
    private val amount: Int = 23;
    private val amountInRow: Int = 4;
    private val dx: Int = 220;
    private val dy: Int = 220;
    private val offsetX: Int = 150;
    private val offsetY: Int = 100;
    private lateinit var saveCanvas: Canvas;

    private var line: Line? = null
    private var startPoint: Point = Point(0, 0);

    private var startFigure: Figure = Rhombus(60F, 60F , 60F);
    private var lastFigure: Figure = Rhombus(60F, 60F , 60F);

    private val paint: Paint = Paint();
    private var lines: MutableList<Line> = mutableListOf<Line>();
    private var circles: MutableList<Circle> = mutableListOf<Circle>();

    init {
        paint.color = Color.parseColor("#FFFFFF")
        paint.alpha = 255
        paint.isAntiAlias = true

        for (i in 0..amount) {
            val countInRow: Int = i % amountInRow
            val rowNumber: Double = Math.ceil((i / amountInRow).toDouble())
            val x: Float = (offsetX + (dx + radius) * countInRow).toFloat()
            val y: Float = (offsetY + (dy + radius) * rowNumber).toFloat()

            if (i == 0) {
                startFigure = Rhombus(x, y , 60F);
                lastFigure = Rhombus(x, y , 60F);
            }else {
                circles.add(Circle(x, y, radius))
            }
        }
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        saveCanvas = canvas;
        canvas.drawColor(Color.parseColor("#151515"))
        drawSavedLines()
        drawLine(line)
        drawCircles(canvas)
    }

    private fun drawCircles(canvas: Canvas) {
        circles.forEach { T ->
            T.draw(canvas, paint)
        }
        startFigure.draw(canvas, paint)
    }

    private fun drawSavedLines() {
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 4F
        lines.forEach { line ->
            drawLine(line)
        }
    }

    fun stopDrawLine() {
        if (line != null) {
            val endCircle: Circle? = checkDotInCircles(line!!.endPoint)
            checkIntersections(line!!)
            if (endCircle != null) {
                line?.endPoint = endCircle.getCenterPoint()
                if (checkCorner(line!!)) {
                    endCircle.setActive()
                    lastFigure = endCircle;
                    lines.add(line!!);
                }
            }
            line = null
        }
        invalidate()
    }

    private fun drawLine(line: Line?) {
        if (line != null) {
            saveCanvas.drawLine(
                line.startPoint.x.toFloat(), line.startPoint.y.toFloat(),
                line.endPoint.x.toFloat(), line.endPoint.y.toFloat(),
                paint
            )
        }
    }

    private fun checkDotInCircles(point: Point): Circle? {
        return circles.firstOrNull { circle: Circle ->
            circle.includeDot(point)
        }
    }

    private fun checkIntersections(line: Line) {
        circles.forEach { circle: Circle ->
            val status = circle.includeLine(line.startPoint, line.endPoint);
            if (status) {
                circle.setActive()
            }
        }
    }


    private fun checkCorner(line: Line): Boolean {
        return (line.startPoint.x == line.endPoint.x
                || line.startPoint.y == line.endPoint.y)
    }

    fun createLine(x: Float, y: Float) {
        //TODO не создавать новую а модифицировать
        line = Line(startPoint, Point(x.toInt(), (y - actionBarOffset).toInt()) )
        invalidate()
    }

    fun startDrawLine(x: Float, y: Float) {
        startPoint = lastFigure.getCenterPoint()
        createLine(x, y)
    }
}