package com.sih.vachana

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.sih.vachana.GraphicOverlay.Graphic
import com.google.firebase.ml.vision.document.FirebaseVisionDocumentText.Word

/**
 * Graphic instance for rendering TextBlock position, size, and ID within an associated graphic
 * overlay view.
 */
class CloudTextGraphic internal constructor(
    private val overlay: GraphicOverlay?,
    private val word: Word?
) :
    Graphic(overlay) {
    private val rectPaint: Paint
    private val textPaint: Paint
    /**
     * Draws the text block annotations for position, size, and raw value on the supplied canvas.
     */
    override fun draw(canvas: Canvas?) {
        checkNotNull(word) { "Attempting to draw a null text." }
        val x = overlay!!.width / 4.0f
        val y = overlay.height / 4.0f
        val wordStr = StringBuilder()
        val wordRect = word.boundingBox
        canvas!!.drawRect(wordRect!!, rectPaint)
        val symbols = word.symbols
        for (m in symbols.indices) {
            wordStr.append(symbols[m].text)
        }
        canvas.drawText(
            wordStr.toString(),
            wordRect.left.toFloat(),
            wordRect.bottom.toFloat(),
            textPaint
        )
    }

    companion object {
        private const val TEXT_COLOR = Color.GREEN
        private const val TEXT_SIZE = 60.0f
        private const val STROKE_WIDTH = 5.0f
    }

    init {
        rectPaint = Paint()
        rectPaint.color = TEXT_COLOR
        rectPaint.style = Paint.Style.STROKE
        rectPaint.strokeWidth = STROKE_WIDTH
        textPaint = Paint()
        textPaint.color = TEXT_COLOR
        textPaint.textSize = TEXT_SIZE
        // Redraw the overlay, as this graphic has been added.
        postInvalidate()
    }
}