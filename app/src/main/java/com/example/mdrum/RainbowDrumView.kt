package com.example.mdrum

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import java.util.Random

class RainbowDrumView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint()
    private val colors: IntArray = context.resources.getIntArray(R.array.rainbow_colors)

    private val random = Random()
    private var startDeg = 0f
    private var endDeg = 0f

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val width = width.toFloat()
        val height = height.toFloat()
        val radius = Math.min(width, height) / 2

        val centerX = width / 2
        val centerY = height / 2

        for (i in colors.indices) {
            paint.color = colors[i]
            canvas.drawArc(
                centerX - radius, centerY - radius, centerX + radius, centerY + radius,
                i * (360f / colors.size), 360f / colors.size, true, paint
            )
        }
    }

    fun rotateDrum() {
        endDeg = (random.nextInt(360) + 360/7).toFloat()
        val rotationAnimator = ObjectAnimator.ofFloat(this, "rotation", startDeg, endDeg)
        startDeg = endDeg
        rotationAnimator.duration = 1000 // Продолжительность анимации в миллисекундах
        rotationAnimator.start()
    }

    fun rotateDrumRandomly() {
        val rotationDuration = getRandomDuration()
        val rotationAngle = getRandomAngle()

        val rotationAnimator = ObjectAnimator.ofFloat(this, "rotation", 0f, rotationAngle)
        rotationAnimator.duration = rotationDuration
        rotationAnimator.start()
    }

    private fun getRandomDuration(): Long {
        return Random().nextInt(3000) + 1000L // от 1000 до 4000 миллисекунд
    }

    private fun getRandomAngle(): Float {
        return Random().nextInt(360).toFloat()
    }

    fun getRandomColor(): Int {
        val random = Random()
        return colors[random.nextInt(colors.size)]
    }

    fun showResult(color: Int) {
        // Используйте ImageView или TextView для отображения результата
    }


}