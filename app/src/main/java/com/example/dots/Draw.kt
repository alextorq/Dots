package com.example.dots

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
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
    private val paint: Paint = Paint();
    private var lines: MutableList<Line> = mutableListOf<Line>();
    private var circles: MutableList<Circle> = mutableListOf<Circle>();

    init {
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
        drawLines()
        drawLine(line)
        drawCircles(canvas)
//        val sizes: IntArray = getSize()
    }

    private fun drawCircles(canvas: Canvas) {
        paint.color = Color.parseColor("#FFFFFF")
        paint.alpha = 255
        paint.isAntiAlias = true

        circles.map { T ->
            T.draw(canvas, paint)
        }
    }

    private fun drawLines() {
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 4F
        lines.map { T ->
            saveCanvas.drawLine(T.startX, T.startY, T.endX, T.endY, paint)
        }
    }


    public fun stopDrawLine() {
        if (line != null) {
            lines.add(line!!);
        }
    }

    private fun drawLine(T: Line?) {
        if (T != null) {
            saveCanvas.drawLine(T.startX, T.startY, T.endX, T.endY, paint)
        }
    }

    fun createLine(x: Float, y: Float) {
        line = Line(startX, startY, x ,y - actionBarOffset)
        invalidate()
    }

    fun startDrawLine(x: Float, y: Float) {
        startX = x;
        startY = y - actionBarOffset;
    }
}