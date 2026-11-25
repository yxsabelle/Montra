package com.example.montraproject

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat

class DonutView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private var values: List<Float> = emptyList()
    private var colors: List<Int> = emptyList()
    private var centerText: String = ""

    private val arcPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.BUTT
    }
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ContextCompat.getColor(context, R.color.text_dark)
        textAlign = Paint.Align.CENTER
        typeface = Typeface.DEFAULT_BOLD
    }
    private val rect = RectF()

    fun setData(values: List<Float>, colors: List<Int>, centerText: String) {
        this.values = values
        this.colors = colors
        this.centerText = centerText
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val stroke = width * 0.16f
        arcPaint.strokeWidth = stroke
        val pad = stroke / 2f + 4f
        rect.set(pad, pad, width - pad, height - pad)

        val total = values.sum()
        if (total <= 0f || colors.isEmpty()) {
            arcPaint.color = ContextCompat.getColor(context, R.color.beige_dark)
            canvas.drawArc(rect, 0f, 360f, false, arcPaint)
        } else {
            var start = -90f
            values.forEachIndexed { i, v ->
                val sweep = v / total * 360f
                arcPaint.color = colors[i % colors.size]
                canvas.drawArc(rect, start, sweep, false, arcPaint)
                start += sweep
            }
        }

        textPaint.textSize = width * 0.13f
        canvas.drawText(centerText, width / 2f, height / 2f + textPaint.textSize / 3f, textPaint)
    }
}