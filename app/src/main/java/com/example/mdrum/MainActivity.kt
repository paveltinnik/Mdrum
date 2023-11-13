package com.example.mdrum

import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var rainbowDrumView: RainbowDrumView
    private lateinit var seekBar: SeekBar
    private lateinit var customImageView: CustomImageView
    private lateinit var customTextView: CustomTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rainbowDrumView = findViewById(R.id.rainbowDrumView)
        customImageView = findViewById(R.id.customImageView)
        customTextView = findViewById(R.id.customTextView)
        seekBar = findViewById(R.id.seekBar)

        rainbowDrumView.setCustomImageView(customImageView)
        rainbowDrumView.setCustomTextView(customTextView)

        initSeekBar()
    }

    fun onRotateButtonClick(view: View) {
        rainbowDrumView.rotateDrumRandomly()
    }

    fun onResetButtonClick(view: View) {
        customImageView.resetView()
        customTextView.resetView()
    }

    private fun initSeekBar() {
        // Установка начального значения ползунка (50%)
        seekBar.progress = 50
        // Установка начального размера барабана
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
}