package io.github.vnicius.githubreposearch.ui.common

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.addTextChangedListener
import com.airbnb.paris.annotations.Attr
import com.airbnb.paris.annotations.Styleable
import com.airbnb.paris.extensions.style
import io.github.vnicius.githubreposearch.R
import io.github.vnicius.githubreposearch.extension.*


/**
 * Created by Vinícius Veríssimo on 5/1/21.
 * github: @vnicius
 * vinicius.matheus252@gmail.com
 */

/**
 * EditText customizado com o estilo de SearchBar
 *
 * Para auxiliar na aplicação dos atrbiutos, está sendo utilizado a biblioteca Paris do Airbnb
 *
 * @see https://github.com/airbnb/paris
 */
@SuppressLint("NonConstantResourceId")
@Styleable("SearchBarView")
class SearchBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = androidx.appcompat.R.attr.editTextStyle
) : AppCompatEditText(context, attrs, defStyleAttr) {

    private var currentEndDrawable: Drawable? = null
    private var onSearchButtonClickedListener: (() -> Unit)? = null
    private var onCancelButtonClickedListener: (() -> Unit)? = null

    init {
        style(attrs)
        style(context.getStyleFromAttr(R.attr.searchBarStyle))

        setEndDrawable(null)
        setupSearchImeOption()
        setupTextChangeListener()
        setupDrawablesClick()
    }

    // region Setup
    private fun setupSearchImeOption() {
        imeOptions = imeOptions or EditorInfo.IME_ACTION_SEARCH
    }

    private fun setupTextChangeListener() {
        addTextChangedListener { text ->
            text?.toString()?.let { textString ->
                if (textString.isBlank()) {
                    setEndDrawable(null)
                } else {
                    setEndDrawable(currentEndDrawable)
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupDrawablesClick() {
        setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val xPosition = event.rawX

                startCompoundDrawable?.bounds?.width()?.let { startDrawableWidth ->
                    if (xPosition <= left + startDrawableWidth) {
                        onSearchButtonClickedListener?.invoke()
                        return@setOnTouchListener true
                    }
                }

                endCompoundDrawable?.bounds?.width()?.let { endDrawableWidth ->
                    if (xPosition >= right - endDrawableWidth) {
                        onCancelButtonClickedListener?.invoke()
                        return@setOnTouchListener true
                    }
                }
            }

            false
        }
    }
    // endregion

    @Attr(R.styleable.SearchBarView_drawableStartCompat)
    fun setStartDrawable(drawable: Drawable?) {
        setStartCompoundDrawable(drawable)
    }

    @Attr(R.styleable.SearchBarView_drawableEndCompat)
    fun setEndDrawable(drawable: Drawable?) {

        if (drawable != null) {
            currentEndDrawable = drawable
        }

        setEndCompoundDrawable(drawable)
    }

    /**
     * Define o callback para quando o botão de busca é clicado
     *
     * @param block - função de callback
     */
    fun setOnSearchButtonClickedListener(block: (() -> Unit)?) {
        onSearchButtonClickedListener = block
    }

    /**
     * Define o callback para quando o botão de cancelar busca é clicado
     *
     * @param block - função de callback
     */
    fun setOnCancelButtonClickedListener(block: (() -> Unit)?) {
        onCancelButtonClickedListener = block
    }
}