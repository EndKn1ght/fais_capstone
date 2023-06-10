package com.bangkit.bangkitcapstone.ui.custom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.bangkit.bangkitcapstone.R

class CustomEditText : AppCompatEditText, View.OnTouchListener {

    private lateinit var clearButtonImage: Drawable
    private lateinit var iconDrawable: Drawable
    private var isEmail: Boolean = false
    private var hintEditText: String? = null

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }

    @SuppressLint("CustomViewStyleable")
    private fun init(context: Context, attrs: AttributeSet?) {
        clearButtonImage = ContextCompat.getDrawable(context, R.drawable.ic_close) as Drawable
        setBackgroundResource(R.drawable.rounded_edittext_bg)
        val ta = context.obtainStyledAttributes(attrs, R.styleable.IconEditText)
        iconDrawable = ta.getDrawable(R.styleable.IconEditText_drawableIcon)!!
        hintEditText = ta.getString(R.styleable.IconEditText_hint)
        ta.recycle()

        setOnTouchListener(this)

        setCompoundDrawablesWithIntrinsicBounds(
            iconDrawable,
            null,
            null,
            null
        )

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s.toString()
                        .isNotEmpty()
                ) showClearButton(iconDrawable) else hideClearButton(iconDrawable)
            }

        })

    }

    private fun showClearButton(icon: Drawable?) {
        setButtonDrawables(startOfTheText = icon, endOfTheText = clearButtonImage)
    }

    private fun hideClearButton(icon: Drawable?) {
        setButtonDrawables(startOfTheText = icon, endOfTheText = null)
    }

    private fun setButtonDrawables(
        startOfTheText: Drawable? = null,
        topOfTheText: Drawable? = null,
        endOfTheText: Drawable? = null,
        bottomOfTheText: Drawable? = null
    ) {
        setCompoundDrawablesWithIntrinsicBounds(
            startOfTheText,
            topOfTheText,
            endOfTheText,
            bottomOfTheText
        )
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        hint = hintEditText
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        if (compoundDrawables[2] != null) {
            val clearButtonStart: Float
            val clearButtonEnd: Float
            var isClearButtonClicked = false
            if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                clearButtonEnd = (clearButtonImage.intrinsicWidth + paddingStart).toFloat()
                when {
                    error == null &&
                            event.x < clearButtonEnd -> isClearButtonClicked = true
                }
            } else {
                clearButtonStart = (width - paddingEnd - clearButtonImage.intrinsicWidth).toFloat()
                when {
                    error == null &&
                            event.x > clearButtonStart -> isClearButtonClicked = true
                }
            }
            if (isClearButtonClicked) {
                return when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        showClearButton(iconDrawable)
                        true
                    }
                    MotionEvent.ACTION_UP -> {
                        when {
                            text != null -> text?.clear()
                        }
                        hideClearButton(iconDrawable)
                        true
                    }
                    else -> false
                }
            } else return false
        }
        return false
    }
}