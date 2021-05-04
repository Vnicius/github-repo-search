package io.github.vnicius.githubreposearch.ui.common

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.res.ResourcesCompat
import com.airbnb.paris.annotations.Attr
import com.airbnb.paris.annotations.Styleable
import com.airbnb.paris.extensions.style
import io.github.vnicius.githubreposearch.R
import io.github.vnicius.githubreposearch.databinding.ViewSearchStateBinding


/**
 * Created by Vinícius Veríssimo on 5/3/21.
 * github: @vnicius
 * vinicius.matheus252@gmail.com
 */
@SuppressLint("NonConstantResourceId")
@Styleable("SearchStateMessageView")
class SearchStateMessageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val viewBinding: ViewSearchStateBinding =
        ViewSearchStateBinding.inflate(LayoutInflater.from(context), this)

    init {
        orientation = VERTICAL
        style(attrs)
    }

    @Attr(R.styleable.SearchStateMessageView_icon)
    fun setIcon(icon: Drawable?) {
        viewBinding.messageIcon.setImageDrawable(icon)
    }

    @Attr(R.styleable.SearchStateMessageView_message)
    fun setMessage(message: String?) {
        viewBinding.messageText.text = message
    }

    fun setIconRes(@DrawableRes resId: Int) {
        setIcon(ResourcesCompat.getDrawable(resources, resId, null))
    }

    fun setMessageRes(@StringRes resId: Int) {
        setMessage(context.getString(resId))
    }
}