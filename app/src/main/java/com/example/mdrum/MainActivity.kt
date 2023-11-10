package com.example.mdrum

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mdrum.pie_chart.PieBean

class MainActivity : AppCompatActivity()
//    , TestView.PieClickListener
{

    private lateinit var rainbowDrumView: RainbowDrumView
    private lateinit var seekBar: SeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rainbowDrumView = findViewById(R.id.rainbowDrumView)
        seekBar = findViewById(R.id.seekBar)

        // Установка начального значения ползунка (50%)
        seekBar.progress = 50

        // Установка начального размера барабана (250)
        var newSize = 200
        var layoutParams = rainbowDrumView.layoutParams
        layoutParams.height = newSize
        rainbowDrumView.layoutParams = layoutParams

        // Отслеживание изменений на ползунке для изменения размера барабана
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                newSize = 1 * progress + 200 // Измените размер по вашему усмотрению
                layoutParams = rainbowDrumView.layoutParams
                layoutParams.height = newSize
                rainbowDrumView.layoutParams = layoutParams
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
    }

//    fun onRotateButtonClick(view: View) {
//        val resultColor = rainbowDrumView.getRandomColor()
//        rainbowDrumView.rotateDrum()
//        rainbowDrumView.showResult(resultColor)
//    }

    fun onRotateButtonClick(view: View) {
        rainbowDrumView.rotateDrumRandomly()
    }

    fun onResetButtonClick() {

    }
}