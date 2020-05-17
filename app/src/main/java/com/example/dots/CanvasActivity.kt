package com.example.dots

import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.dots.interfaces.FigurePosition
import com.example.dots.interfaces.ScreenSizes
import com.example.dots.utils.CalculateSizes
import com.example.dots.utils.sizeParams

class CustomView: AppCompatActivity() {
    private lateinit var model: LevelModel;
    private var heightCanvas: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main2)

        this.updateModel()
        val Game: Draw = findViewById(R.id.view)

        Game.addListner { count ->
            updateStep(count)
        }
        Game.post{
            heightCanvas = Game.height
            this.updateModel()
        }

        val resetLevelButton: ImageButton = findViewById(R.id.resetLevel)

        resetLevelButton.setOnClickListener{
            updateModel()
        }

        super.onCreate(savedInstanceState)
    }




    fun updateModel() {
        model = LevelModel()
        var sizes: ScreenSizes = sizeParams(this)
        heightCanvas?.let { it -> sizes.height = it }

        val calculateSizes = CalculateSizes(model);
        val figurePosition: FigurePosition = calculateSizes.calc(sizes);

        val Game: Draw = findViewById(R.id.view)

        val padding: Int = figurePosition.ofx.toInt();
        val toolbar: LinearLayout = findViewById(R.id.toolbar)
        toolbar.setPadding(padding, 0, padding, 0)

        Game.setModel(figurePosition)

        updateStep(0)
    }



    fun updateStep(count: Int) {
        val TextureView: TextView = findViewById(R.id.step)
        TextureView.setText("${count}/${model.amountSteps}")
    }
}
