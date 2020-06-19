package com.example.dots.activities

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.dots.GameManger
import com.example.dots.LevelRepositoryJson
import com.example.dots.R
//import com.example.dots.utils.cast


class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)


        val resumeLink: LinearLayout = findViewById(R.id.resumeLink)
        resumeLink.setOnClickListener{
            val intent = Intent(this, GameManger::class.java)
            startActivity(intent)
        }


        val newGame: LinearLayout = findViewById(R.id.newGameLink)
        newGame.setOnClickListener{
            val intent = Intent(this, GameManger::class.java)
            startActivity(intent)
        }

    }
}
