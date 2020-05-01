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
    private val dx: Int = 220;
    private val dy: Int = 220;
    private val offsetX: Int = 100;
    private val offsetY: Int = 100;
    private lateinit var saveCanvas: Canvas;

    private var startX: Float = 0.0f;
    private var startY: Float = 0.0f;

    private var endX: Float = 0F;
    private var endY: Float = 0F;

    private val paint: Paint = Paint();

    fun getSize(): IntArray {
        val width: Int = parentContext.getResources().getDisplayMetrics().widthPixels
        val height: Int = parentContext.getResources().getDisplayMetrics().heightPixels

        return intArrayOf(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        saveCanvas = canvas;
        canvas.drawColor(Color.parseColor("#151515"))
        drawCircles(canvas)
//        val sizes: IntArray = getSize()
    }

    private fun drawCircles(canvas: Canvas) {
        paint.style = Paint.Style.STROKE
        paint.setStrokeWidth(4F)
        paint.setColor(Color.parseColor("#FFFFFF"))
        paint.alpha = 255
        paint.isAntiAlias = true

        for (i in 0..amount) {
            val countInRow: Int = i % amountInRow
            val rowNumber: Double = Math.ceil((i / amountInRow).toDouble())

            val x: Float = (offsetX+ (dx + radius) * countInRow).toFloat()
            val y: Float = (offsetY + (dy + radius) * rowNumber).toFloat()

            canvas.drawCircle(x, y, radius, paint)

        }
        saveCanvas.drawLine(startX, startY, endX, endY, paint);
    }

    fun drawLine(x: Float, y: Float) {
//        Log.d("event", "x: ${x}, y: ${y}")

        endX = x;
        endY = y;

        invalidate()
    }

    fun startDrawLine(x: Float, y: Float) {
        startX = x;
        startY = y;
    }
}