package com.example.dots

import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.os.Message
import android.os.PersistableBundle
import android.util.Log
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dots.activities.MenuActivity
import com.example.dots.figures.Line
import com.example.dots.figures.RequiredCircle
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
    private var stepAmount: Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main2)
        ViewGame = findViewById(R.id.view)

        ViewGame.post {
            heightCanvas = ViewGame.height
            this.initGame()
        }

        setListeners()
        super.onCreate(savedInstanceState)
    }

    fun stopDrawLine(line: Line) {
        val statusIntersectinCross = checkIntersections(line)
        if (!statusIntersectinCross) {
            val figure: Figure? = checkDotInFigures(line.endPoint)
            figure?.let { circle ->
                line.endPoint = circle.getCenterPoint()
                if (checkCorner(line) && !somePoint(line)) {
                    when (figure::class.java.simpleName) {
                        "FinishCircle" -> {
                           checkFinish()
                        }
                    }
                    val linesAmount: Int = ViewGame.addLine(line)
                    ViewGame.setFigureActive(figure)
                    updateStep(linesAmount)
                }
            }
        }
        val status = checkStepsAreOver()
        if (status) {
            stopDraw()
            showMessage("STEPS ARE OVER")
        }
    }

    fun finishGame() {
        showMessage("YOU ARE WIN")
    }

    fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun checkFinish() {
        val allCirclePass  = checkIsAllRequiredCircleActive()
        if (allCirclePass) {
            finishGame()
        }else {
            showMessage("YOU MUST PASS ALL REQUIRED DOTS")
        }
        stopDraw()
    }

    private fun checkIsAllRequiredCircleActive(): Boolean {
       return figurePosition.requiredCircle.all { it.isActive }
    }

    fun checkStepsAreOver(): Boolean {
        return stepAmount >= model.amountSteps
    }

    fun stopDraw() {
        ViewGame.canDraw = false
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

    private fun checkDotInFigures(point: Point): Figure? {
        return figurePosition.figures.firstOrNull { figure: Figure ->
            figure.includeDot(point)
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
        heightCanvas?.let { sizes.height = it }
        stepAmount = 0;
        val calculateSizes = CalculatePositionFigure(model);
        figurePosition = calculateSizes.calc(sizes);
        ViewGame.setModel(figurePosition)
        ViewGame.setGameManger(this)

        setPadding()
        updateStep(0)
    }

    private fun setPadding() {
        val padding: Int = figurePosition.ofx.toInt();
        val toolbar: LinearLayout = findViewById(R.id.toolbar)
        toolbar.setPadding(padding, 0, padding, 0)
    }

    fun updateStep(count: Int) {
        stepAmount = count
        val TextureView: TextView = findViewById(R.id.step)
        TextureView.setText("${count}/${model.amountSteps}")
    }


    private fun setListeners() {
        val resetLevelButton: ImageButton = findViewById(R.id.resetLevel)
        resetLevelButton.setOnClickListener{
            initGame()
        }

        val menuLink: ImageButton = findViewById(R.id.menuLink)
        menuLink.setOnClickListener{
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
    }
}
