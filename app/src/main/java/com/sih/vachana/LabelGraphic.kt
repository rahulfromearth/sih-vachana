package com.sih.vachana

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.text.TextPaint

/**
 * Graphic instance for rendering image labels.
 */
class LabelGraphic internal constructor(
    overlay: GraphicOverlay?,
    labels: List<String>
) :
    GraphicOverlay.Graphic(overlay) {
    private val textPaint: Paint
    private val bgPaint: Paint
    private val overlay: GraphicOverlay?
    private val labels: List<String>
    @Synchronized
    override fun draw(canvas: Canvas?) {
        val x: Float = overlay!!.width / 4.0f
        var y: Float = overlay.height / 4.0f
        for (label in labels) {
            drawTextWithBackground(label, x, y, TextPaint(textPaint), bgPaint, canvas)
            y = y - 62.0f
        }
    }

    private fun drawTextWithBackground(
        text: String, x: Float, y: Float, paint: TextPaint,
        bgPaint: Paint, canvas: Canvas?
    ) {
        val fontMetrics = paint.fontMetrics
        canvas?.drawRect(
            Rect(
                x.toInt(), (y + fontMetrics.top).toInt(),
                (x + paint.measureText(text)).toInt(), (y + fontMetrics.bottom).toInt()
            ), bgPaint
        )
        canvas?.drawText(text, x, y, textPaint)
    }

    init {
        this.overlay = overlay
        this.labels = labels
        textPaint = Paint()
        textPaint.color = Color.WHITE
        textPaint.textSize = 60.0f
        bgPaint = Paint()
        bgPaint.color = Color.BLACK
        bgPaint.alpha = 50
    }
}