package com.example.dots

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View

class Draw(private val parentContext: Context): View(parentContext) {
    private val radius: Float = 30F;
    private val amount: Int = 23;
    private val amountInRow: Int = 4;
    private val actionBarOffset = 215

    private val dx: Int = 220;
    private val dy: Int = 220;
    private val offsetX: Int = 150;
    private val offsetY: Int = 100;
    private lateinit var saveCanvas: Canvas;

    private var line: Line? = null
    private var startX: Float = 0F;
    private var startY: Float = 0F;

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
            val x: Float = (offsetX+ (dx + radius) * countInRow).toFloat()
            val y: Float = (offsetY + (dy + radius) * rowNumber).toFloat()
            circles.add(Circle(x, y, radius))
        }
    }

    fun getSize(): IntArray {
        val width: Int = parentContext.getResources().getDisplayMetrics().widthPixels
        val height: Int = parentContext.getResources().getDisplayMetrics().heightPixels

        return intArrayOf(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        saveCanvas = canvas;
        canvas.drawColor(Color.parseColor("#151515"))
        drawSavedLines()
        drawLine(line)
        drawCircles(canvas)
//        val sizes: IntArray = getSize()
    }

    private fun drawCircles(canvas: Canvas) {
        circles.map { T ->
            T.draw(canvas, paint)
        }
        startFigure.draw(canvas, paint)
    }

    private fun drawSavedLines() {
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 4F
        lines.map { T ->
            saveCanvas.drawLine(T.startX, T.startY, T.endX, T.endY, paint)
        }
    }


    fun stopDrawLine() {
        if (line != null) {
            val endCircle: Circle? = checkDotInCircles(line!!.endX, line!!.endY)
            if (endCircle != null) {
                line?.endX = endCircle.cx
                line?.endY = endCircle.cy
                endCircle.setActive()
                lastFigure = endCircle;
                lines.add(line!!);
            } else {
                /*Если конечная точка линии не принадлежит ни одному кругу сбрасываем линию*/
              line = null
            }
        }
        invalidate()
    }

    private fun drawLine(T: Line?) {
        if (T != null) {
            saveCanvas.drawLine(T.startX, T.startY, T.endX, T.endY, paint)
        }
    }

    private fun checkDotInCircles(x: Float, y: Float): Circle? {
        return circles.firstOrNull { circle: Circle ->
            circle.includeDot(x, y)
        }
    }

    fun createLine(x: Float, y: Float) {
        line = Line(startX, startY, x,y - actionBarOffset)
        invalidate()
    }

    fun startDrawLine(x: Float, y: Float) {
        startX = lastFigure.getCenterPoint().x.toFloat();
        startY = lastFigure.getCenterPoint().y.toFloat();
        createLine(x, y)
    }
}