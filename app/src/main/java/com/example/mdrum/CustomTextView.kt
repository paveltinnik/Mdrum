package com.example.mdrum

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CustomTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private lateinit var canvas: Canvas
    private val textPaint: Paint = Paint()
    private var text: String? = null

    init {
        textPaint.apply {
            textSize = 200F
            color = Color.RED
            style = Paint.Style.FILL
            textAlign = Paint.Align.CENTER
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Позиционирование текста по центру CustomView
        val x = width / 2f
        val y = height / 2f - (textPaint.descent() + textPaint.ascent()) / 2f

        text?.let {
            canvas.drawText(it, x, y, textPaint)
        }
    }

    fun drawText() {
        text = "Hello"
        invalidate()
    }

    fun resetView() {
        text = null
        invalidate()
    }
}