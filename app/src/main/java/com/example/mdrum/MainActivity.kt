package com.example.mdrum

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mdrum.pie_chart.PieBean


class MainActivity : AppCompatActivity(), TestView.PieClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pieChart = findViewById<TestView>(R.id.simple_pie_chart)

        val pie1 = PieBean()
        pie1.percent = 100 / 7f
        pie1.picColor = R.color.colorRed
        pieChart.setPieBean(pie1)

        val pie2 = PieBean()
        pie2.percent = 100 / 7f
        pie2.picColor = R.color.colorOrange
        pieChart.setPieBean(pie2)

        val pie3 = PieBean()
        pie3.percent = 100 / 7f
        pie3.picColor = R.color.colorYellow
        pieChart.setPieBean(pie3)

        val pie4 = PieBean()
        pie4.percent = 100 / 7f
        pie4.picColor = R.color.colorGreen
        pieChart.setPieBean(pie4)

        val pie5 = PieBean()
        pie5.percent = 100 / 7f
        pie5.picColor = R.color.colorBlue
        pieChart.setPieBean(pie5)

        val pie6 = PieBean()
        pie6.percent = 100 / 7f
        pie6.picColor = R.color.colorIndigo
        pieChart.setPieBean(pie6)

        val pie7 = PieBean()
        pie7.percent = 100 / 7f
        pie7.picColor = R.color.colorViolet
        pieChart.setPieBean(pie7)

        pieChart.startAnim()

        pieChart.setPieClickListener(this)
    }

    override fun onPieClick(position: Int, percent: Float) {
        Toast.makeText(this,"当前点击的position："+position + ";百分比："+percent,Toast.LENGTH_SHORT).show()
    }
}