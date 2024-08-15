package demo.atomofiron.hapticengine

import android.content.Context
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.O
import android.os.Build.VERSION_CODES.P
import android.os.Build.VERSION_CODES.S
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.view.View

private const val DEFAULT_DURATION = 10L
private const val DEFAULT_AMPLITUDE = 15

fun View.bzz(duration: Long = DEFAULT_DURATION, amplitude: Int = DEFAULT_AMPLITUDE) {
    if (isHapticFeedbackEnabled && SDK_INT >= O) {
        getVibrator(context)?.vibrate(VibrationEffect.createOneShot(duration, amplitude))
    }
}

private fun getVibrator(context: Context): Vibrator? {
    return when {
        SDK_INT < P -> null
        SDK_INT < S -> context.getSystemService(Context.VIBRATOR_SERVICE)
            .let { it as Vibrator }
            .takeIf { it.hasVibrator() }
        else -> context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE)
            .let { it as VibratorManager }
            .defaultVibrator
            .takeIf { it.hasVibrator() }
    }
}
