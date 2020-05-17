package com.example.dots

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.TextureView
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.example.dots.figures.Circle
import com.example.dots.figures.Line
import com.example.dots.interfaces.Figure
import com.example.dots.interfaces.FigurePosition
import com.example.dots.interfaces.ScreenSizes
import com.example.dots.utils.sizeParams

class Draw(context: Context, attrs: AttributeSet): View(context, attrs) {

    private lateinit var saveCanvas: Canvas;
    private lateinit var figurePosition: FigurePosition;

    private var line: Line? = null
    private val paint: Paint = Paint();
    private var lines: MutableList<Line> = mutableListOf<Line>();

    private lateinit var lastFigure: Figure;
    private lateinit var finishFigure: Figure;
    private lateinit var circles: MutableList<Circle>;
    private lateinit var figures: MutableList<Figure>;
    private lateinit var crosses: MutableList<Figure>;


    private lateinit var calback: (count: Int) -> Unit;

    @SuppressLint("ResourceType")
    private val backgroundColor: Int = Color.parseColor(getResources().getString(R.color.colorBackground))

    init {
        paint.color = Color.parseColor("#FFFFFF")
        paint.alpha = 255
        paint.isAntiAlias = true
    }

    public fun setModel(position: FigurePosition) {
        figurePosition = position;
        lastFigure = position.lastFigure;
        circles = position.circles
        figures = position.figures
        crosses = position.crosses
        finishFigure = position.finishFigure
        lines.clear()
        postInvalidateOnAnimation()
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
            val statusIntersectinCross = checkIntersections(it)
            if (!statusIntersectinCross) {
                val endCircle: Circle? = checkDotInCircles(it.endPoint)
                endCircle?.let { circle ->
                    it.endPoint = circle.getCenterPoint()
                    if (checkCorner(it) && !somePoint(it)) {
                        endCircle.setActive(this)
                        lastFigure = endCircle;
                        lines.add(it);
                        updateStep(lines.size)
                    }
                }
                val finish = finishFigure.includeDot(it.endPoint)
                if (finish) {
                    it.endPoint = finishFigure.getCenterPoint()
                    if (checkCorner(it)) {
                        lastFigure = finishFigure;
                        lines.add(it);
                        updateStep(lines.size)
                    }
                }
            }
        }
        line = null
        postInvalidateOnAnimation()
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


    private fun checkFinish(point: Point): Boolean {
        return lastFigure.includeDot(point)
    }

    private fun checkDotInCircles(point: Point): Circle? {
        return circles.firstOrNull { circle: Circle ->
            circle.includeDot(point)
        }
    }

    private fun checkIntersections(line: Line): Boolean {
        var status = false;
        crosses.forEach { cross: Figure ->
            val statusInclude = cross.includeLine(line.startPoint, line.endPoint);
            if (!status) {
                status =  statusInclude
            }
        }
        return status
    }

    /*Проверка на то что прямая не под углом*/
    private fun checkCorner(line: Line): Boolean {
        return (line.startPoint.x == line.endPoint.x
                || line.startPoint.y == line.endPoint.y)
    }
    private fun somePoint(line: Line):Boolean {
        return (line.startPoint.x == line.endPoint.x
                && line.startPoint.y == line.endPoint.y)
    }

    fun createLine(x: Float, y: Float) {
        //TODO не создавать новую а модифицировать
        line = Line(
            lastFigure.getCenterPoint(),
            Point(x.toInt(), y.toInt())
        )
        postInvalidateOnAnimation()
    }

    fun startDrawLine(x: Float, y: Float) {
        createLine(x, y)
    }


    fun addListner(callback: (count: Int) -> Unit) {
        this.calback = callback
    }

    fun updateStep(step: Int) {
        this.calback(step)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                startDrawLine(x, y)
            }
            MotionEvent.ACTION_MOVE -> {
               createLine(x, y)
            }
            MotionEvent.ACTION_UP-> {
               stopDrawLine()
            }
        }
        return true
    }
}