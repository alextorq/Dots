package com.example.dots
import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity

class CustomView: AppCompatActivity() {

    lateinit var draw: Draw;

    override fun onCreate(savedInstanceState: Bundle?) {
        draw = Draw(this);
        super.onCreate(savedInstanceState)
        setContentView(draw)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                draw.startDrawLine(x, y)

            }
            MotionEvent.ACTION_MOVE -> {
                draw.drawLine(x, y)
            }
        }
        return true
    }

}