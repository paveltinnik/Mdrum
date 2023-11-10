package com.example.mdrum

import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

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
        var newSize = 350
        var layoutParams = rainbowDrumView.layoutParams
        layoutParams.height = newSize
        rainbowDrumView.layoutParams = layoutParams

        // Отслеживание изменений на ползунке для изменения размера барабана
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                newSize = 1 * progress + 300 // Измените размер по вашему усмотрению
                layoutParams = rainbowDrumView.layoutParams
                layoutParams.height = newSize
                rainbowDrumView.layoutParams = layoutParams
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
    }

    fun onRotateButtonClick(view: View) {
        rainbowDrumView.rotateDrumRandomly()
    }

    fun onResetButtonClick(view: View) {

    }
}