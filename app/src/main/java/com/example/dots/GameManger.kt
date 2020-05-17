package com.example.dots

import android.graphics.Point
import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.dots.figures.Circle
import com.example.dots.figures.Line
import com.example.dots.interfaces.Figure
import com.example.dots.interfaces.FigurePosition
import com.example.dots.interfaces.ScreenSizes
import com.example.dots.utils.CalculatePositionFigure
import com.example.dots.utils.sizeParams

class GameManger: AppCompatActivity() {
    private lateinit var model: LevelModel;
    private var heightCanvas: Int? = null
    private lateinit var figurePosition: FigurePosition
    private lateinit var ViewGame: Draw;

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main2)
        ViewGame= findViewById(R.id.view)

        this.initGame()

        ViewGame.post {
            heightCanvas = ViewGame.height
            this.initGame()
        }

        val resetLevelButton: ImageButton = findViewById(R.id.resetLevel)

        resetLevelButton.setOnClickListener{
            initGame()
        }

        super.onCreate(savedInstanceState)
    }

    fun stopDrawLine(line: Line) {
        val statusIntersectinCross = checkIntersections(line)
        if (!statusIntersectinCross) {
            val endCircle: Circle? = checkDotInCircles(line.endPoint)
            endCircle?.let { circle ->
                line.endPoint = circle.getCenterPoint()
                if (checkCorner(line) && !somePoint(line)) {
                    val linesAmount: Int = ViewGame.addLine(line, endCircle)
                    updateStep(linesAmount)
//                    set
                }
            }
            val finish = figurePosition.finishFigure.includeDot(line.endPoint)
            if (finish) {
                line.endPoint = figurePosition.finishFigure.getCenterPoint()
                if (checkCorner(line)) {
                    figurePosition.lastFigure = figurePosition.finishFigure;
                    val linesAmount: Int = ViewGame.addLine(line, endCircle)
                    updateStep(linesAmount)
                }
            }
        }
    }


    private fun checkFinish(point: Point): Boolean {
        return figurePosition.lastFigure.includeDot(point)
    }

    private fun somePoint(line: Line):Boolean {
        return (line.startPoint.x == line.endPoint.x
                && line.startPoint.y == line.endPoint.y)
    }

    /*Проверка на то что прямая не под углом*/
    private fun checkCorner(line: Line): Boolean {
        return (line.startPoint.x == line.endPoint.x
                || line.startPoint.y == line.endPoint.y)
    }

    private fun checkDotInCircles(point: Point): Circle? {
        return figurePosition.circles.firstOrNull { circle: Circle ->
            circle.includeDot(point)
        }
    }


    private fun checkIntersections(line: Line): Boolean {
        var status = false;
        figurePosition.crosses.forEach { cross: Figure ->
            val statusInclude = cross.includeLine(line.startPoint, line.endPoint);
            if (!status) {
                status =  statusInclude
            }
        }
        return status
    }




    fun initGame() {
        model = LevelModel()
        val sizes: ScreenSizes = sizeParams(this)
        heightCanvas?.let { it -> sizes.height = it }

        val calculateSizes = CalculatePositionFigure(model);
        figurePosition = calculateSizes.calc(sizes);
        ViewGame.setModel(figurePosition)

        setPadding()
        updateStep(0)
    }


    private fun setPadding() {
        val padding: Int = figurePosition.ofx.toInt();
        val toolbar: LinearLayout = findViewById(R.id.toolbar)
        toolbar.setPadding(padding, 0, padding, 0)
    }



    fun updateStep(count: Int) {
        val TextureView: TextView = findViewById(R.id.step)
        TextureView.setText("${count}/${model.amountSteps}")
    }
}
