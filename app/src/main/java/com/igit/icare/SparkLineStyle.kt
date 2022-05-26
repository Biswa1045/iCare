package com.igit.icare

import android.content.Context
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.LineDataSet
import java.time.format.DateTimeFormatter

class SparkLineStyle  constructor(private val context: Context) {



    fun styleChart(lineChart: LineChart) = lineChart.apply {
        xAxis.isEnabled = true

        axisLeft.apply {
            isEnabled = false
            axisMinimum = 0f
            axisMaximum = 11f
        }
       axisRight.apply {
           isGranularityEnabled = true
           setDrawGridLines(true)
           setDrawAxisLine(false)
           textColor = ContextCompat.getColor(context, R.color.sky)
       }
     //   xAxis.apply {
         //   axisMinimum = 0f
        //    axisMaximum = 23f
       //     isGranularityEnabled = true
        //    granularity = 4f
        //    setDrawGridLines(false)
         //   setDrawAxisLine(false)
       //     position = XAxis.XAxisPosition.BOTTOM

          //  valueFormatter = TimeValueFormatter()

         //   textColor = ContextCompat.getColor(context, R.color.black)
     //   }

        setTouchEnabled(true)
        isDragEnabled = true
        setScaleEnabled(false)
        setPinchZoom(false)

        description = null
        legend.isEnabled = false
    }



    fun styleLineDataSet(lineDataSet: LineDataSet)=lineDataSet.apply {
        color = ContextCompat.getColor(context, R.color.gold)
        valueTextColor = ContextCompat.getColor(context, R.color.white)
        setDrawValues(false)
        lineWidth = 1.5f
        isHighlightEnabled = true
        setDrawHighlightIndicators(false)
        setDrawCircles(false)
     //   mode = LineDataSet.Mode.CUBIC_BEZIER
        setDrawFilled(true)
        fillDrawable = ContextCompat.getDrawable(context, R.drawable.bg_spark)
    }


}