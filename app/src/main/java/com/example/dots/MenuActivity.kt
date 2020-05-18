package com.example.dots

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity


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
