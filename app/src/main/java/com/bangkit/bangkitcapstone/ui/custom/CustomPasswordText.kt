package com.bangkit.bangkitcapstone.ui.custom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.bangkit.bangkitcapstone.R

class CustomPasswordText : AppCompatEditText, View.OnTouchListener {

    private lateinit var showPasswordIcon: Drawable
    private lateinit var hidePasswordIcon: Drawable
    private lateinit var iconDrawable: Drawable


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
    private fun init(ctx: Context, attrs: AttributeSet?) {
        showPasswordIcon = ContextCompat.getDrawable(context, R.drawable.ic_visibility) as Drawable
        hidePasswordIcon =
            ContextCompat.getDrawable(context, R.drawable.ic_visibility_off) as Drawable
        setBackgroundResource(R.drawable.rounded_edittext_bg)

        val ta = context.obtainStyledAttributes(attrs, R.styleable.IconEditText)
        iconDrawable = ta.getDrawable(R.styleable.IconEditText_drawableIcon)!!
        ta.recycle()

        transformationMethod = PasswordTransformationMethod.getInstance()
        setCompoundDrawablesWithIntrinsicBounds(
            iconDrawable,
            null,
            showPasswordIcon,
            null
        )

        setOnTouchListener(this)
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        if (compoundDrawables[2] != null) {
            val passwordButtonStart: Float
            val passwordButtonEnd: Float
            var isPasswordButtonClicked = false
            if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                passwordButtonEnd = (showPasswordIcon.intrinsicWidth + paddingStart).toFloat()
                when {
                    event.x < passwordButtonEnd -> isPasswordButtonClicked = true
                }
            } else {
                passwordButtonStart =
                    (width - paddingEnd - showPasswordIcon.intrinsicWidth).toFloat()
                when {
                    event.x > passwordButtonStart -> isPasswordButtonClicked = true
                }
            }
            return if (isPasswordButtonClicked) {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        if (transformationMethod == PasswordTransformationMethod.getInstance()) {
                            showPassword(iconDrawable)
                        } else {
                            hidePassword(iconDrawable)
                        }
                        true
                    }
                    else -> false
                }
            } else false
        }
        return false
    }

    private fun showPassword(icon: Drawable?) {
        transformationMethod = HideReturnsTransformationMethod.getInstance()
        setButtonDrawable(startOfTheText = icon, endOfTheText = hidePasswordIcon)
    }

    private fun hidePassword(icon: Drawable?) {
        transformationMethod = PasswordTransformationMethod.getInstance()
        setButtonDrawable(startOfTheText = icon, endOfTheText = showPasswordIcon)
    }

    private fun setButtonDrawable(
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
        hint = resources.getString(R.string.password_hint)
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START

    }
}