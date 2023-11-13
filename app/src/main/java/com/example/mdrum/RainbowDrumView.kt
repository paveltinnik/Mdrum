package com.example.mdrum

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import java.util.Random

class RainbowDrumView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint()
    private val colors: IntArray = context.resources.getIntArray(R.array.rainbow_colors)
    private var rotationAnimator: ObjectAnimator? = null
    private var customImageView: CustomImageView? = null
    private var customTextView: CustomTextView? = null

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

    fun rotateDrumRandomly() {
        endDeg = (random.nextInt(360)).toFloat()
        rotationAnimator = ObjectAnimator.ofFloat(this, "rotation", startDeg, endDeg)
        rotationAnimator!!.duration = 1000 // Продолжительность анимации в миллисекундах
        rotationAnimator!!.addListener(animationListener)
        rotationAnimator!!.start()
    }

    private val animationListener = object : Animator.AnimatorListener {
        override fun onAnimationStart(animator: Animator) {}

        override fun onAnimationEnd(animator: Animator) {
            if (getSegmentNumber() in intArrayOf(0, 2, 4, 6)) {
                customImageView?.resetView()
                customTextView?.drawText()
            } else {
                customTextView?.resetView()
                customImageView?.loadImage()
            }
        }

        override fun onAnimationCancel(animator: Animator) {}

        override fun onAnimationRepeat(animator: Animator) {}
    }

    private fun getSegmentNumber(): Int {
        // Получение угла поворота барабана
        val rotation = this@RainbowDrumView.rotation % 360
        // Определение цвета по углу поворота
        val segmentAngle = 360f / colors.size
        val segmentIndex = (rotation / segmentAngle).toInt()
        return segmentIndex
    }

    fun setCustomImageView(customImageView: CustomImageView) {
        this.customImageView = customImageView
    }

    fun setCustomTextView(customTextView: CustomTextView) {
        this.customTextView = customTextView
    }
}