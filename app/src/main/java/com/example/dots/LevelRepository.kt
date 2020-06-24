package com.example.dots

import android.content.Context
import android.content.SharedPreferences
import android.preference.Preference
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.io.InputStream
import java.io.OutputStreamWriter
import com.example.dots.interfaces.LevelRepository
import java.util.prefs.Preferences


val DATABASE_VERSION = 9
val DATABASE_NAME = "Dots.db"

typealias levelMap = MutableMap<String, Any>

fun readJSONFrom(context: Context, fileName: String): String {
    var json: String = ""
    try {
        val inputStream: InputStream = context.openFileInput(fileName)
        json = inputStream.bufferedReader().use{it.readText()}
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
    return json
}

fun readJSONFromAsset(context: Context, fileName: String): String {
    var json: String = ""
    try {
        val inputStream: InputStream = context.getAssets().open(fileName)
        json = inputStream.bufferedReader().use{it.readText()}
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
    return json
}

val PREFIX = "level"


//class LevelRepository(val context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
//
//    private val TABLE_NAME = "levels"
//
//
//    private val SQL_CREATE_TABLE = "CREATE TABLE $TABLE_NAME (id INTEGER PRIMARY KEY ASC AUTOINCREMENT, amountFigure DOUBLE  NOT NULL, amountFigureInRow DOUBLE, circleRadius DOUBLE, amountSteps INT, currentStep INT, lines TEXT, figures TEXT);"
//
//    private fun genInsertSQL(model: String): String {
//        val SQL_INSERT = "INSERT INTO $TABLE_NAME (figures, lines, currentStep, amountSteps, circleRadius, amountFigureInRow, amountFigure) VALUES ('${model}', NULL, 0, 6, 4, 6, 36);"
//        return SQL_INSERT;
//    }
//
//    override fun onCreate(sqLiteDatabase : SQLiteDatabase?) {
//        sqLiteDatabase?.execSQL(SQL_CREATE_TABLE)
//        val model = readJSONFromAsset(this.context, "level.json");
//        model?.let{
//            val sql = genInsertSQL(model)
//            sqLiteDatabase?.execSQL(sql)
//        }
//    }
//
//    fun getAll(db: SQLiteDatabase): List<LevelModel> {
//        val SQL = "SELECT * FROM $TABLE_NAME;"
//        val cursor: Cursor = db.rawQuery(SQL, null)
//        val levels: MutableList<levelMap> = mutableListOf<levelMap>()
//        if (cursor.moveToFirst()) {
//            while (!cursor.isAfterLast()) {
//                levels.add(getOneLevelFromCursor(cursor))
//                cursor.moveToNext()
//            }
//        }
//        cursor.close()
//        return levels.map { cast(it) };
//    }
//
//
//    fun getOneLevelFromCursor(cursor: Cursor): levelMap {
//        val levelMap: levelMap = mutableMapOf()
//        val columnNames = cursor.columnNames
//        val columnAmount = cursor.columnCount
//        for (x in 0 until columnAmount) {
//            val type = cursor.getType(x);
//            val name = columnNames[x];
//            var value: Any = "";
//            when(type) {
//                Cursor.FIELD_TYPE_FLOAT-> value = cursor.getFloat(x)
//                Cursor.FIELD_TYPE_INTEGER -> value = cursor.getInt(x)
//                Cursor.FIELD_TYPE_STRING -> value = cursor.getString(x)
//                else -> {}
//            }
//            levelMap[name] = value;
//        }
//        return levelMap;
//    }
//
//
//    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
//        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
//        onCreate(db);
//    }
//}


class LevelRepositoryJson(val context: Context): LevelRepository {

    private lateinit var pref: SharedPreferences;

    init {
        replaceInitialFiles()
    }

    fun replaceInitialFiles() {
        val files = context.fileList();

        if  (files.size < 2) {
            val levels = getAllLevels(true)
            levels.forEachIndexed { index, level ->
                saveLevel(level, index)
            }
        }

    }

    fun getNameFiles(initial: Boolean): MutableList<String> {
        val listNames: MutableList<String> = mutableListOf<String>();
        var files = context.fileList();
        if (initial) {
            files = context.assets.list("");
        }
        files?.let {
            for (name in it) {
                if (name.startsWith(PREFIX)) {
                    listNames.add(name)
                }
            }
        }
        return listNames;
    }

    override fun getAllLevels(initial: Boolean): MutableList<LevelModel> {
        val listLevelModel: MutableList<LevelModel> = mutableListOf<LevelModel>();
        for (name in getNameFiles(initial)) {
            var model = "";
            if (initial) {
                model = readJSONFromAsset(this.context, name);
            } else {
                model = readJSONFrom(this.context, name);
            }
            val listPersonType = object : TypeToken<LevelModel>() {}.type
            var figuresClassArray: LevelModel = Gson().fromJson(model, listPersonType)
            listLevelModel.add(figuresClassArray)
        }
        return listLevelModel;
    }


    override fun saveLevel(levelModel: LevelModel, name: Int): LevelModel {
        try {
            val data = Gson().toJson(levelModel)
            val outputStreamWriter = OutputStreamWriter(context.openFileOutput("$PREFIX$name.json", Context.MODE_PRIVATE))
            outputStreamWriter.write(data)
            outputStreamWriter.close()
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        return levelModel
    }



    fun getCurrentLevelName(): Int {
        val pref = context.getSharedPreferences("dots.levels", Context.MODE_PRIVATE)
        return pref.getInt("levelName", 0)
    }

    fun setLastLevelName(name: Int): Int {
        pref = context.getSharedPreferences("dots.levels", Context.MODE_PRIVATE)
        pref.edit().putInt("levelName", name);
        return name;
    }


}


