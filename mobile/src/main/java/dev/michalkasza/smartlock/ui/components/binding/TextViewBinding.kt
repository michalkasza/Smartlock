package dev.michalkasza.smartlock.ui.components.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.*

object TextViewBinding {
    @JvmStatic
    @BindingAdapter(value = ["timestampExtractDate"], requireAll = true)
    fun timestampExtractDate(textView: TextView, timestamp: Long?) {
        timestamp?.let {
            textView.text = SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH).format(timestamp)
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["timestampExtractHours"], requireAll = true)
    fun timestampExtractHours(textView: TextView, timestamp: Long?) {
        timestamp?.let {
            textView.text = SimpleDateFormat("HH:mm:ss", Locale.ENGLISH).format(timestamp)
        }
    }
}