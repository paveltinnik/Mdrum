package com.example.mdrum

import android.animation.Animator
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

    private var animationListener: Animator.AnimatorListener? = null

    var rotationAnimator: ObjectAnimator? = null

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

    // Установка слушателя для завершения анимации
    fun setAnimationListener(listener: Animator.AnimatorListener) {
        animationListener = listener
    }

    fun rotateDrum() {
        endDeg = (random.nextInt(360) + 360).toFloat()
        val rotationAnimator = ObjectAnimator.ofFloat(this, "rotation", startDeg, endDeg)
        startDeg = endDeg
        rotationAnimator.duration = 1000 // Продолжительность анимации в миллисекундах
        rotationAnimator.repeatCount = 2
        rotationAnimator.start()
    }

    fun rotateDrumRandomly() {
        endDeg = (random.nextInt(360)).toFloat()
        rotationAnimator = ObjectAnimator.ofFloat(this, "rotation", startDeg, endDeg)
        rotationAnimator!!.duration = 1000 // Продолжительность анимации в миллисекундах

        rotationAnimator!!.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {}

            override fun onAnimationEnd(animator: Animator) {}

            override fun onAnimationCancel(animator: Animator) {}

            override fun onAnimationRepeat(animator: Animator) {}
        })

//        rotationAnimator.addListener(object : Animator.AnimatorListener {
//
//            override fun onAnimationStart(p0: Animator) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onAnimationEnd(animation: Animator) {
////                // Получение угла поворота барабана
////                val rotation = this@RainbowDrumView.rotation % 360
////
////                // Определение цвета по углу поворота
////                val segmentAngle = 360f / colors.size
////                val segmentIndex = (rotation / segmentAngle).toInt()
////                val topSegmentColor = colors[segmentIndex]
////
////                // Вызов слушателя с цветом верхнего сегмента
////                animationListener?.onAnimationEnd(animation)
//////                animationListener?.onAnimationEndWithColor(topSegmentColor)
//            }
//
//            override fun onAnimationCancel(p0: Animator) {}
//
//            override fun onAnimationRepeat(p0: Animator) {}
//        })

        rotationAnimator!!.start()


    }

    private fun getRandomDuration(): Long {
        return Random().nextInt(3000) + 1000L // от 1000 до 4000 миллисекунд
    }

    private fun getRandomAngle(): Float {
        return Random().nextInt(360).toFloat()
    }
}