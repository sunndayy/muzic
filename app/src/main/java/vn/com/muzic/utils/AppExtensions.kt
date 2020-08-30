package vn.com.muzic.utils

import android.content.ContentResolver
import android.content.Context
import android.content.res.Resources
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.TypedValue
import androidx.annotation.*
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import java.io.FileNotFoundException

// region Android
@ColorInt
fun Context.getThemeColor(
    @AttrRes attrResId: Int,
    @ColorRes fallbackColorResId: Int
): Int {
    val tv = TypedValue()
    return if (theme.resolveAttribute(attrResId, tv, true)) {
        tv.data
    } else {
        ContextCompat.getColor(this, fallbackColorResId)
    }
}

fun Context.dp(size: Float): Int =
    kotlin.math.ceil(size * this.resources.displayMetrics.density).toInt()

fun Context.dp2(size: Float): Int =
    kotlin.math.floor(size * this.resources.displayMetrics.density).toInt()

fun Context.dp2px(size: Float): Float =
    size * this.resources.displayMetrics.density

fun Context.sp(size: Float): Float =
    size * this.resources.displayMetrics.scaledDensity

fun Context.px2sp(pixel: Float): Float =
    pixel / resources.displayMetrics.scaledDensity

fun Context.dimen(@DimenRes resource: Int): Int = resources.getDimensionPixelSize(resource)

fun Context.attrResourceId(@AttrRes attr: Int): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(attr, typedValue, true)
    return typedValue.resourceId
}

fun Context.getColorCompat(@ColorRes colorRes: Int): Int =
    ContextCompat.getColor(this, colorRes)

fun Context.getDrawableUri(@DrawableRes drawableRes: Int): Uri =
    Uri.Builder()
        .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
        .authority(this.resources.getResourcePackageName(drawableRes))
        .appendPath(this.resources.getResourceTypeName(drawableRes))
        .appendPath(this.resources.getResourceEntryName(drawableRes))
        .build()

fun Context.getFontCompat(@FontRes fontRes: Int): Typeface? =
    try {
        ResourcesCompat.getFont(this, fontRes)
    } catch (e: Resources.NotFoundException) {
        null
    }

fun Context.getDrawable(uri: Uri): Drawable? = contentResolver.openInputStream(uri)?.let {
    try {
        it.use { stream ->
            Drawable.createFromStream(stream, uri.toString())
        }
    } catch (e: FileNotFoundException) {
        null
    }
}

fun Context.getDrawableCompat(@DrawableRes res: Int) = ContextCompat.getDrawable(this, res)
// end region