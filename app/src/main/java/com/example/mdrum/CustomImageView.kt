package com.example.mdrum

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target

class CustomImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var imageBitmap: Bitmap? = null
    private val imagePaint: Paint = Paint()
    private val PATH = "https://loremflickr.com/640/360"
    private lateinit var canvas: Canvas

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        this.canvas = canvas

        imageBitmap?.let {
            canvas.drawBitmap(it, 0f, 0f, imagePaint)
        }
    }

    fun loadImage() {
        val target = object : Target {
            override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
                imageBitmap = bitmap
                invalidate() // Перерисовываем Custom View после загрузки изображения
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: android.graphics.drawable.Drawable?) {}

            override fun onPrepareLoad(placeHolderDrawable: android.graphics.drawable.Drawable?) {}
        }

        Picasso.get().load(PATH).into(target)
    }

    fun resetView() {
        imageBitmap = null
        invalidate()
    }
}