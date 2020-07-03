package com.example.dots

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Point
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.dots.activities.MenuActivity
import com.example.dots.figures.Line
import com.example.dots.interfaces.Figure
import com.example.dots.interfaces.FigurePosition
import com.example.dots.interfaces.ScreenSizes
import com.example.dots.utils.CalculatePositionFigure
import com.example.dots.utils.sizeParams


class GameManger: AppCompatActivity() {
    private var heightCanvas: Int? = null
    private lateinit var figurePosition: FigurePosition
    private lateinit var ViewGame: Draw;
    private lateinit var levels: List<LevelModel>;
    private lateinit var currentLevel: LevelModel;
    private lateinit var repository: LevelRepositoryJson;
    private var levelName: Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main2)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ViewGame = findViewById(R.id.view)

        levels = getLevels();
        levelName = repository.getCurrentLevelName()

        ViewGame.post {
            heightCanvas = ViewGame.height
            currentLevel = levels[levelName]
            this.initGame()
        }

        setListeners()
        super.onCreate(savedInstanceState)
    }


    fun getLevels(): List<LevelModel> {
        repository = LevelRepositoryJson(this)
        return repository.getAllLevels(false)
    }


    fun switchNextLevel() {
        repository.saveLevel(currentLevel, levelName)
        levelName++;
        repository.setLastLevelName(levelName)
        currentLevel = levels[levelName]
        initGame()
    }


    fun stopDrawLine(line: Line) {
        val statusIntersectinCross = checkIntersections(line)
        if (!statusIntersectinCross) {
            val figure: Figure? = checkDotInFigures(line.endPoint)
            val startFigure : Figure? = checkDotInFigures(line.startPoint)
            figure?.let { circle ->
                if (!circle.isCanAssign) {return}
                line.endPoint = circle.getCenterPoint()
                if (checkCorner(line) && !somePoint(line)) {
                    when (figure::class.java.simpleName) {
                        "FinishCircle" -> {
                           checkFinish()
                        }
                    }
                    ViewGame.addLine(line)
                    startFigure?.playingField?.let { currentLevel.setline(it,  figure.playingField) }

                    ViewGame.setFigureActive(figure)
                    updateStep()
                }
            }
        } else {
            playSound();
        }
        checkStepsAreOver()

    }

    fun finishGame() {
        showFinModal()
    }


    @SuppressLint("ClickableViewAccessibility")
    fun showFinModal() {
        val parent = findViewById<View>(R.id.toolbar)
        val popupView: View = LayoutInflater.from(this).inflate(R.layout.win, null)
        val width = LinearLayout.LayoutParams.FILL_PARENT
        val height = LinearLayout.LayoutParams.FILL_PARENT
        val focusable = true

        val popupWindow = PopupWindow(popupView, width, height, focusable)
        popupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0)

        popupWindow.setTouchInterceptor{ _, value ->
            popupWindow.dismiss() ;
            switchNextLevel()
            true
        }

    }

    fun playSound() {
        val mp: MediaPlayer = MediaPlayer.create(this, R.raw.error)
        mp.start()
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

    fun checkStepsAreOver(): Unit {
        if (currentLevel.lines.size >= currentLevel.amountSteps) {
            stopDraw()
            showMessage("STEPS ARE OVER")
        }
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
        val sizes: ScreenSizes = sizeParams(this)
        heightCanvas?.let { sizes.height = it }
        val calculateSizes = CalculatePositionFigure(currentLevel);

        figurePosition = calculateSizes.calc(sizes);
        ViewGame.setModel(figurePosition)
        ViewGame.setGameManger(this)
        ViewGame.invalidate()
        updateStep()
    }


    fun updateStep() {
        val TextureView: TextView = findViewById(R.id.step)
        TextureView.setText("${currentLevel.lines.size}/${currentLevel.amountSteps}")
    }


    private fun setListeners() {
        val resetLevelButton: ImageButton = findViewById(R.id.resetLevel)
        resetLevelButton.setOnClickListener{
            currentLevel.resetLevel()
            repository.saveLevel(currentLevel, levelName)
            initGame()
        }

        val menuLink: ImageButton = findViewById(R.id.menuLink)
        menuLink.setOnClickListener{
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
    }
}
