package com.sih.vachana

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.Log
import com.sih.vachana.GraphicOverlay.Graphic
import com.google.firebase.ml.vision.text.FirebaseVisionText

/**
 * Graphic instance for rendering TextBlock position, size, and ID within an associated graphic
 * overlay view.
 */
class TextGraphic internal constructor(
    overlay: GraphicOverlay?,
    private val element: FirebaseVisionText.Element?
) :
    Graphic(overlay) {
    private val rectPaint: Paint
    private val textPaint: Paint
    /**
     * Draws the text block annotations for position, size, and raw value on the supplied canvas.
     */
    override fun draw(canvas: Canvas?) {
        Log.d(TAG, "on draw text graphic")
        checkNotNull(element) { "Attempting to draw a null text." }
        // Draws the bounding box around the TextBlock.
        val rect = RectF(element.boundingBox)
        canvas?.drawRect(rect, rectPaint)
        // Renders the text at the bottom of the box.
        canvas?.drawText(element.text, rect.left, rect.bottom, textPaint)
    }

    companion object {
        private const val TAG = "TextGraphic"
        private const val TEXT_COLOR = Color.RED
        private const val TEXT_SIZE = 54.0f
        private const val STROKE_WIDTH = 4.0f
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