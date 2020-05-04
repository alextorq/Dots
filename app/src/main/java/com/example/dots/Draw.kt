package com.example.dots

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.view.View
import com.example.dots.figures.Circle
import com.example.dots.figures.Line
import com.example.dots.interfaces.Figure

class Draw(parentContext: Context, model: LevelModel): View(parentContext) {
    private lateinit var saveCanvas: Canvas;

    private var line: Line? = null
    private var lastFigure: Figure = model.startFigure;
    private val actionBarOffset = model.yOffset

    private val paint: Paint = Paint();
    private var lines: MutableList<Line> = mutableListOf<Line>();
    private var circles: MutableList<Circle> = model.circles;
    private var figures: MutableList<Figure> = model.figures;

    @SuppressLint("ResourceType")
    private val backgroundColor: Int = Color.parseColor(getResources().getString(R.color.colorBackground))

    init {
        paint.color = Color.parseColor("#FFFFFF")
        paint.alpha = 255
        paint.isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        saveCanvas = canvas;
        canvas.drawColor(backgroundColor)
        drawSavedLines()
        drawCurrentLine(line)
        drawFigures(canvas)
    }

    private fun drawFigures(canvas: Canvas) {
        figures.forEach { T ->
            T.draw(canvas, paint)
        }
    }

    private fun drawSavedLines() {
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 4F
        lines.forEach { line ->
            drawCurrentLine(line)
        }
    }

    fun stopDrawLine() {
        line?.let {
            checkIntersections(it)
            val endCircle: Circle? = checkDotInCircles(it.endPoint)
            endCircle?.let { circle ->
                it.endPoint = circle.getCenterPoint()
                if (checkCorner(it)) {
                    endCircle.setActive()
                    lastFigure = endCircle;
                    lines.add(it);
                }
            }
            line = null
        }
        invalidate()
    }

    private fun drawCurrentLine(line: Line?) {
        line?.let {
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

    /*Проверка на то что прямая не под углом*/
    private fun checkCorner(line: Line): Boolean {
        return (line.startPoint.x == line.endPoint.x
                || line.startPoint.y == line.endPoint.y)
    }

    fun createLine(x: Float, y: Float) {
        //TODO не создавать новую а модифицировать
        line = Line(
            lastFigure.getCenterPoint(),
            Point(x.toInt(), (y - actionBarOffset).toInt())
        )
        invalidate()
    }

    fun startDrawLine(x: Float, y: Float) {
        createLine(x, y)
    }
}