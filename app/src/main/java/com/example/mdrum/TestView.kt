package com.example.mdrum

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.mdrum.pie_chart.PieBean

class TestView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var mRadius = 100f
    private var mRectF: RectF? = null

    private var mPercentPaint: Paint? = null
    private var mPathPaint: Paint? = null
    private var mLinePath: Path? = null
    private var mTextPaint: Paint? = null
    private var mTextSize = 20f

    private var mTouchDegree = 0f

    private var mDataList: MutableList<PieBean>? = null
    private var mProgressList: FloatArray? = null

    private var mShouldShowText = true

    private var mPieClickListener: PieClickListener? = null

    private var mCenterX = 0f
    private var mCenterY = 0f

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val suggestWidth = MeasureSpec.getSize(widthMeasureSpec)
        val suggestHeight = MeasureSpec.getSize(heightMeasureSpec)

        if (widthMode == MeasureSpec.AT_MOST || heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mRadius.toInt() * 4, mRadius.toInt() * 4)
        } else {
            setMeasuredDimension(suggestWidth, suggestHeight)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        mCenterX = width.div(2).toFloat()
        mCenterY = height.div(2).toFloat()
        mRectF =
            RectF(mCenterX - mRadius, mCenterY - mRadius, mCenterX + mRadius, mCenterY + mRadius)
    }

    init {
//        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TestView)
            mRadius = typedArray.getDimension(R.styleable.TestView_pieChartRadius, mRadius)
            mShouldShowText =
                typedArray.getBoolean(R.styleable.TestView_pieChartShowText, mShouldShowText)
            mTextSize = typedArray.getDimension(R.styleable.TestView_pieChartTextSize, mTextSize)
//        }

        mDataList = ArrayList()

        mLinePath = Path()
        mPercentPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPercentPaint!!.style = Paint.Style.FILL

        mPathPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPathPaint!!.style = Paint.Style.STROKE
        mPathPaint!!.strokeWidth = 1f

        mTextPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mTextPaint!!.style = Paint.Style.STROKE
        mTextPaint!!.textSize = mTextSize

        typedArray.recycle()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        mCenterX = width.div(2).toFloat()
        mCenterY = height.div(2).toFloat()

        var startAngle = 0f
        var pathPercent = 0f

        for (index in 0 until mDataList!!.size) {
            mLinePath!!.reset()
            val float = mDataList!![index].percent!!
            val color = mDataList!![index].picColor!!
            val percent = float.div(100) * 360

            mPathPaint!!.color = color
            mPercentPaint!!.color = color
            mTextPaint!!.color = color

            pathPercent = startAngle + percent.div(2)

            // Draw text and path
            if (mShouldShowText) {
                if (pathPercent >= 0 && pathPercent < 90) {
                    drawPathAndText(float, pathPercent, 0f, 1, 1, 0, false, canvas!!)
                } else if (pathPercent >= 90 && pathPercent < 180) {
                    drawPathAndText(float, pathPercent, 90f, -1, 1, 1, true, canvas!!)
                } else if (pathPercent >= 180 && pathPercent < 270) {
                    drawPathAndText(float, pathPercent, 180f, -1, -1, 1, false, canvas!!)
                } else {
                    drawPathAndText(float, pathPercent, 270f, 1, -1, 0, true, canvas!!)
                }
            }

            // Draw arc
            if (mTouchDegree > startAngle && mTouchDegree < (startAngle + percent)) {
                mRectF = RectF(
                    mCenterX - mRadius - mRadius.div(10), mCenterY - mRadius - mRadius.div(10),
                    mCenterX + mRadius + mRadius.div(10), mCenterY + mRadius + mRadius.div(10)
                )
                canvas!!.drawArc(
                    mRectF!!,
                    startAngle,
                    mProgressList!![index],
                    true,
                    mPercentPaint!!
                )
            } else {
                mRectF = RectF(
                    mCenterX - mRadius,
                    mCenterY - mRadius,
                    mCenterX + mRadius,
                    mCenterY + mRadius
                )
                canvas!!.drawArc(
                    mRectF!!,
                    startAngle,
                    mProgressList!![index],
                    true,
                    mPercentPaint!!
                )
            }

            startAngle += percent
        }
    }

    private fun drawPathAndText(
        float: Float, pathPercent: Float, countDegree: Float,
        xCoefficient: Int, yCoefficient: Int, textOffset: Int,
        quadrant: Boolean, canvas: Canvas
    ) {

        val sin = Math.sin(Math.toRadians((pathPercent - countDegree).toDouble()))
        val cos = Math.cos(Math.toRadians((pathPercent - countDegree).toDouble()))
        val pathX = (mCenterX + xCoefficient * mRadius * (if (quadrant) sin else cos)).toFloat()
        val pathY = (mCenterY + yCoefficient * mRadius * (if (quadrant) cos else sin)).toFloat()
        val nextX =
            (mCenterX + xCoefficient * (mRadius.div(2) * 3) * (if (quadrant) sin else cos)).toFloat()
        val nextY =
            (mCenterY + yCoefficient * (mRadius.div(2) * 3) * (if (quadrant) cos else sin)).toFloat()
        val endX =
            (mCenterX + xCoefficient * (mRadius.div(2) * 3) * (if (quadrant) sin else cos) + xCoefficient * mRadius.div(
                4
            )).toFloat()
        mLinePath!!.moveTo(pathX, pathY)
        mLinePath!!.lineTo(nextX, nextY)
        mLinePath!!.lineTo(endX, nextY)

        canvas.drawPath(mLinePath!!, mPathPaint!!)
        canvas.drawText(
            float.toString().plus("%"),
            endX - textOffset * mTextPaint!!.measureText(float.toString().plus("%")),
            nextY,
            mTextPaint!!
        )
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {
                if (judgeTouchRange(event.x, event.y)) {
                    mTouchDegree = getRotationBetweenLines(event.x, event.y)

                    var temp = 0f
                    for (index in 0 until mDataList!!.size) {
                        val float = mDataList!![index].percent!!
                        val percent = float.div(100) * 360
                        if (mTouchDegree > temp && mTouchDegree <= temp + percent) {
                            mPieClickListener!!.onPieClick(index, float)
                        }
                        temp += percent
                    }
                    invalidate()
                }

            }

            MotionEvent.ACTION_UP -> {

            }
        }
        return true

    }

    private fun judgeTouchRange(x: Float, y: Float): Boolean {
        val xAbs = Math.abs(x - mCenterX)
        val yAbs = Math.abs(y - mCenterY)
        val pow = Math.pow(xAbs.toDouble(), 2.0) + Math.pow(yAbs.toDouble(), 2.0)
        if (Math.sqrt(pow) <= mRadius) {
            return true
        }
        return false
    }

    fun startAnim() {
        mProgressList = FloatArray(mDataList!!.size)

        val animatorSet = AnimatorSet()
        val list = mutableListOf<ValueAnimator>()

        for (index in 0 until mDataList!!.size) {
            val float = mDataList!![index].percent!!
            val percent = float.div(100) * 360
            val animator = ValueAnimator.ofFloat(percent)
            animator.duration = 2500
            animator.addUpdateListener {
                mProgressList!![index] = it.animatedValue as Float
                invalidate()
            }

            list.add(animator)
        }

        for (index in 0 until mDataList!!.size - 1) {
            animatorSet.play(list[index]).with(list[index + 1])
        }

        animatorSet.start()
    }

    private fun getRotationBetweenLines(xInView: Float, yInView: Float): Float {

        val centerX = width.div(2).toFloat()
        val centerY = height.div(2).toFloat()
        var rotation = 0.0

        val k1 = (centerY - centerY).toDouble() / (centerX * 2 - centerX)
        val k2 = (yInView - centerY).toDouble() / (xInView - centerX)
        val tmpDegree = Math.atan(Math.abs(k1 - k2) / (1 + k1 * k2)) / Math.PI * 180

        if (xInView > centerX && yInView < centerY) {  // Quadrant 1
            rotation = tmpDegree + 270
        } else if (xInView > centerX && yInView > centerY) { // Quadrant 4
            rotation = tmpDegree
        } else if (xInView < centerX && yInView > centerY) { // Quadrant 3
            rotation = tmpDegree + 90
        } else if (xInView < centerX && yInView < centerY) { // Quadrant 2
            rotation = tmpDegree + 180
        } else if (xInView == centerX && yInView < centerY) {
            rotation = 0.0
        } else if (xInView == centerX && yInView > centerY) {
            rotation = 180.0
        }
        return rotation.toFloat()
    }

    fun setPieClickListener(pieClickListener: PieClickListener) {
        mPieClickListener = pieClickListener
    }

    fun setPieBean(bean: PieBean) {
        mDataList!!.add(bean)
        invalidate()
    }

    interface PieClickListener {
        fun onPieClick(position: Int, percent: Float)
    }
}