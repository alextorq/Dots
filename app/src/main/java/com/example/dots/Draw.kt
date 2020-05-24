package com.example.dots

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.dots.figures.Circle
import com.example.dots.figures.Line
import com.example.dots.interfaces.Figure
import com.example.dots.interfaces.FigurePosition


class Draw(context: Context, attrs: AttributeSet): View(context, attrs) {

    private lateinit var saveCanvas: Canvas;

    private var line: Line? = null
    private val paint: Paint = Paint();
    private var lines: MutableList<Line> = mutableListOf<Line>();

    private lateinit var lastFigure: Figure;
    private var figures: MutableList<Figure> = mutableListOf<Figure>();
    public var canDraw: Boolean = true;

    private lateinit var gameManager: GameManger;


    @SuppressLint("ResourceType")
    private val backgroundColor: Int = Color.parseColor(getResources().getString(R.color.colorBackground))

    init {
        paint.color = Color.parseColor("#FFFFFF")
        paint.alpha = 255
        paint.isAntiAlias = true
    }

    public fun setModel(position: FigurePosition) {
        canDraw = true
        lastFigure = position.lastFigure;
        figures = position.figures
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

    fun addLine(line: Line): Int {
        lines.add(line);
        return lines.size
    }

    fun setGameManger(context: GameManger) {
        gameManager = context
    }

    fun setFigureActive(figure: Figure) {
        figure.setActive(this)
        lastFigure = figure;
    }

    private fun drawSavedLines() {
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 4F
        lines.forEach { line ->
            drawCurrentLine(line)
        }
    }

    private fun stopDrawLine() {
        line?.let {
            gameManager.stopDrawLine(it)
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

    fun createLine(x: Float, y: Float) {
        //TODO не создавать новую а модифицировать
        if (!canDraw) {
            return
        }
        line = Line(
            lastFigure.getCenterPoint(),
            Point(x.toInt(), y.toInt())
        )
        postInvalidateOnAnimation()
    }

    fun startDrawLine(x: Float, y: Float) {
        createLine(x, y)
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