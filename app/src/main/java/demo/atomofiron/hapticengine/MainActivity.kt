package demo.atomofiron.hapticengine

import android.graphics.Color
import android.os.Bundle
import android.view.HapticFeedbackConstants
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import demo.atomofiron.hapticengine.ui.theme.HapticTheme

val types = arrayOf(
    "LONG_PRESS" to HapticFeedbackConstants.LONG_PRESS,
    "VIRTUAL_KEY" to HapticFeedbackConstants.VIRTUAL_KEY,
    "KEYBOARD_TAP" to HapticFeedbackConstants.KEYBOARD_TAP,
    "CLOCK_TICK" to HapticFeedbackConstants.CLOCK_TICK,
    "CALENDAR_DATE (hidden)" to 5,
    "CONTEXT_CLICK" to HapticFeedbackConstants.CONTEXT_CLICK,
    "KEYBOARD_PRESS (27)" to HapticFeedbackConstants.KEYBOARD_PRESS,
    "KEYBOARD_RELEASE (27)" to HapticFeedbackConstants.KEYBOARD_RELEASE,
    "VIRTUAL_KEY_RELEASE (27)" to HapticFeedbackConstants.VIRTUAL_KEY_RELEASE,
    "TEXT_HANDLE_MOVE (27)" to HapticFeedbackConstants.TEXT_HANDLE_MOVE,
    "ENTRY_BUMP (hidden)" to 10,
    "DRAG_CROSSING (hidden)" to 11,
    "GESTURE_START (30)" to HapticFeedbackConstants.GESTURE_START,
    "GESTURE_END (30)" to HapticFeedbackConstants.GESTURE_END,
    "EDGE_SQUEEZE (hidden)" to 14,
    "EDGE_RELEASE (hidden)" to 15,
    "CONFIRM (30)" to HapticFeedbackConstants.CONFIRM,
    "REJECT (30)" to HapticFeedbackConstants.REJECT,
    "ROTARY_SCROLL_TICK (hidden)" to 18,
    "ROTARY_SCROLL_ITEM_FOCUS (hidden)" to 19,
    "ROTARY_SCROLL_LIMIT (hidden)" to 20,
    "TOGGLE_ON (34)" to HapticFeedbackConstants.TOGGLE_ON,
    "TOGGLE_OFF (34)" to HapticFeedbackConstants.TOGGLE_OFF,
    "GESTURE_THRESHOLD_ACTIVATE (34)" to HapticFeedbackConstants.GESTURE_THRESHOLD_ACTIVATE,
    "GESTURE_THRESHOLD_DEACTIVATE (34)" to HapticFeedbackConstants.GESTURE_THRESHOLD_DEACTIVATE,
    "DRAG_START (34)" to HapticFeedbackConstants.DRAG_START,
    "SEGMENT_TICK (34)" to HapticFeedbackConstants.SEGMENT_TICK,
    "SEGMENT_FREQUENT_TICK (34)" to HapticFeedbackConstants.SEGMENT_FREQUENT_TICK,
)

// haptic max 55 - Nothing Phone(1)
// haptic max 100 - Oneplus 7T
private const val MIN_DURATION = 1f
private const val MAX_DURATION = 256f
private const val MIN_AMPLITUDE = 1f
// VibrationEffect.MAX_AMPLITUDE
private const val MAX_AMPLITUDE = 255f

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.isHapticFeedbackEnabled = true
        window.decorView.post {
            val one = Color.parseColor("#01808080")
            enableEdgeToEdge(navigationBarStyle = SystemBarStyle.auto(one, one))
            window.navigationBarColor = one
        }
        setContent {
            Content {
                window.decorView
            }
        }
    }
}

@Composable
fun Content(view: (() -> View)?) {
    HapticTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val haptic = LocalHapticFeedback.current
            var duration by remember { mutableFloatStateOf(55f) }
            var amplitude by remember { mutableFloatStateOf(255f) }
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = innerPadding,
            ) {
                item {
                    Text(text = "View")
                }
                items(types.size) {
                    Button(onClick = { view?.invoke()?.performHapticFeedback(types[it].second) }) {
                        Text(text = types[it].first)
                    }
                }
                item {
                    Text(text = "Compose")
                }
                item {
                    Button(onClick = { haptic.performHapticFeedback(HapticFeedbackType.LongPress) }) {
                        Text(text = "LongPress")
                    }
                }
                item {
                    Button(onClick = { haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove) }) {
                        Text(text = "TextHandleMove")
                    }
                }
                item {
                    Text(text = "custom")
                }
                item {
                    Slider(
                        value = duration,
                        valueRange = MIN_DURATION..MAX_DURATION,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        onValueChange = { duration = it },
                    )
                }
                item {
                    Slider(
                        value = amplitude,
                        valueRange = MIN_AMPLITUDE..MAX_AMPLITUDE,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        onValueChange = { amplitude = it },
                    )
                }
                item {
                    Button(onClick = { view?.invoke()?.bzz(duration.toLong(), amplitude.toInt()) }) {
                        Text(text = "vibration dur=${duration.toInt()}ms amp=${amplitude.toInt()}")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() = Content(null)
