package tj.task.graphqlleadsapp.extension

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import tj.task.graphqlleadsapp.R
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*


fun String.toDateFormattedString(): String? {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val date = inputFormat.parse(this)
    val outputFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return date?.let { outputFormat.format(it) }
}

inline fun <reified T : Serializable> Bundle.serializable(key: String): T? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getSerializable(key) as? T
}

fun TextInputEditText.makeEnabledButClickable() = with(this) {
    isFocusable = false
    isClickable = true
    isLongClickable = true
    keyListener = null
}

fun TextInputEditText.showDefaultText(text: String) = with(this) {
    setText(text)
    setTextColor(ContextCompat.getColor(context, R.color.grey_2))
}

fun TextInputEditText.showOriginalText(text: String?) = with(this) {
    setText(text ?: "")
    setTextColor(ContextCompat.getColor(context, R.color.dark_blue))
}

fun View.showSnackBar(message: String? = null, duration: Int = Snackbar.LENGTH_LONG) {
    Snackbar.make(this, message ?: "", duration).show()
}
