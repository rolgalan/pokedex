package me.rolgalan.pokemon.elements


import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.label_value_textview.view.*
import me.rolgalan.pokemon.R

/**
 * Created by Roldán Galán on 06/11/2017.
 */

class LabelValueTextView : LinearLayout {

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int,
                defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        inflate(getContext(), R.layout.label_value_textview, this)
        parseCustomAttrs(context, attrs)
    }

    private fun parseCustomAttrs(context: Context, attrs: AttributeSet?) {

        if (attrs == null) {
            return
        }

        val a = context.obtainStyledAttributes(attrs, R.styleable.LabelValue)
        val label = a.getString(R.styleable.LabelValue_label)
        val value = a.getString(R.styleable.LabelValue_value)

        if (label != null) {
            setLabel(label)
        }
        if (value != null) {
            setValue(value)
        }

        a.recycle()
    }

    fun setValue(value: String) {
        textview_value.setText(value)
    }

    fun setLabel(label: String) {
        textview_label.setText(label);
    }

}
