package com.example.dots

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.dots.interfaces.FigurePosition
import com.example.dots.interfaces.ScreenSizes
import com.example.dots.utils.CalculateSizes
import com.example.dots.utils.sizeParams

class CustomView: AppCompatActivity() {
    private lateinit var model: LevelModel;

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main2)

        this.updateModel(null)
        val Game: Draw = findViewById(R.id.view)

        Game.addListner { count ->
            updateStep(count)
        }

        Game.post{
            this.updateModel(Game.height);
        }

        updateStep(0)
        super.onCreate(savedInstanceState)
    }

    fun updateModel(height: Int?) {
        model = LevelModel()
        var sizes: ScreenSizes = sizeParams(this)
        height?.let { it -> sizes.height = it }

        var calculateSizes: CalculateSizes = CalculateSizes(model);
        var figurePosition: FigurePosition = calculateSizes.calc(sizes);

        val Game: Draw = findViewById(R.id.view)

        val padding: Int = figurePosition.ofx.toInt();
        val toolbar: LinearLayout = findViewById(R.id.toolbar)
        toolbar.setPadding(padding, 0, padding, 0)

        Game.setModel(figurePosition)
    }



    fun updateStep(count: Int) {
        val TextureView: TextView = findViewById(R.id.step)
        TextureView.setText("${count}/${model.amountSteps}")
    }
}
