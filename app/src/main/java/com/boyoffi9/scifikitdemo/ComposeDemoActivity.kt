package com.boyoffi9.scifikitdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.boyoffi9.scifikit.compose.GlitchText
import com.boyoffi9.scifikit.compose.HoloRadar
import com.boyoffi9.scifikit.compose.HudFrame
import com.boyoffi9.scifikit.core.DecodeMode
import com.boyoffi9.scifikit.core.GlitchMode
import com.boyoffi9.scifikit.core.RadarMode
import com.boyoffi9.scifikit.core.TouchMode

/** Jetpack Compose demo. Same look as the Views demo, same painters underneath. */
class ComposeDemoActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { Demo(onBack = { finish() }) }
    }
}

@Composable
private fun Demo(onBack: () -> Unit) {
    var replay by remember { mutableIntStateOf(0) }
    var decode by remember { mutableStateOf(DecodeMode.Classic) }
    var glitch by remember { mutableStateOf(GlitchMode.Standard) }
    var touch by remember { mutableStateOf(TouchMode.Burst) }
    var radar by remember { mutableStateOf(RadarMode.Classic) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF05080C))
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        GlitchText(text = "SCIFIKIT // COMPOSE", fontSize = 30.sp, glitch = Calm, replayKey = replay)
        Dim("Tap any glitch text to glitch it.")

        LiveConfig()

        HudFrame(modifier = Modifier.fillMaxWidth()) {
            Column {
                GlitchText(text = "SYSTEM ONLINE", fontSize = 22.sp, glitch = Calm, replayKey = replay)
                Dim("All subsystems nominal.")
            }
        }

        HudFrame(
            modifier = Modifier.fillMaxWidth(),
            accentColor = Color(0xFFFF2D6F),
            chamfer = 22.dp,
        ) {
            GlitchText(
                text = "INTRUSION DETECTED",
                fontSize = 22.sp,
                splitColorA = Color(0xFF00E5FF),
                splitColorB = Color(0xFFFFC400),
                glitch = Calm,
                intensity = 1.6f,
                decodeDurationMs = 1400L,
                replayKey = replay,
            )
        }

        HudFrame(
            modifier = Modifier.fillMaxWidth(),
            accentColor = Color(0xFFB388FF),
            sweep = false,
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                ModePicker(DecodeMode.entries, decode) { decode = it }
                GlitchText(
                    text = "ACCESS GRANTED // NODE 7F",
                    fontSize = 20.sp,
                    glitch = Calm,
                    decode = decode.config,
                    replayKey = replay,
                )
            }
        }

        HudFrame(
            modifier = Modifier.fillMaxWidth(),
            accentColor = Color(0xFFFF2D6F),
            sweep = false,
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                ModePicker(GlitchMode.entries, glitch) { glitch = it }
                GlitchText(text = "INTRUSION DETECTED", fontSize = 22.sp, glitch = glitch.config)
            }
        }

        HudFrame(
            modifier = Modifier.fillMaxWidth(),
            accentColor = Color(0xFF7CFFB2),
            sweep = false,
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                ModePicker(TouchMode.entries, touch) { touch = it }
                GlitchText(text = "PRESS AND DRAG HERE", fontSize = 26.sp, glitch = Calm, touch = touch.config)
            }
        }

        HudFrame(
            modifier = Modifier.fillMaxWidth(),
            sweep = false,
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                ModePicker(RadarMode.entries, radar) { radar = it }
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    HoloRadar(size = 180.dp, radar = radar.config)
                }
            }
        }

        HudFrame(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { replay++ },
            accentColor = Color(0xFFFFC400),
            sweep = false,
        ) {
            GlitchText(text = "REPLAY DECODE >", fontSize = 18.sp, glitch = Calm)
        }

        HudFrame(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onBack),
            accentColor = Color(0xFF7CFFB2),
            sweep = false,
        ) {
            GlitchText(text = "< BACK TO VIEWS DEMO", fontSize = 18.sp, glitch = Calm)
        }
    }
}

private val Calm = GlitchMode.OnTouch.config

private val LiveColors = listOf(
    Color(0xFF00E5FF),
    Color(0xFFFF2D6F),
    Color(0xFFFFC400),
    Color(0xFF7CFFB2),
    Color(0xFFB388FF),
)

@Composable
private fun LiveConfig() {
    var step by remember { mutableIntStateOf(0) }
    val color = LiveColors[step % LiveColors.size]
    val mode = DecodeMode.entries[step % DecodeMode.entries.size]

    HudFrame(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { step++ },
        accentColor = color,
        chamfer = (10 + step % 4 * 6).dp,
        scanlines = step % 2 == 0,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            GlitchText(
                text = "LIVE // ${mode.name.uppercase()}",
                fontSize = 18.sp,
                splitColorA = LiveColors[(step + 1) % LiveColors.size],
                decode = mode.config,
                glitch = GlitchMode.entries[step % GlitchMode.entries.size].config,
            )
            HoloRadar(
                size = 140.dp,
                accentColor = color,
                radar = RadarMode.entries[step % RadarMode.entries.size].config,
                rings = 2 + step % 5,
                rotationPeriodMs = if (step % 2 == 0) 4000L else 1800L,
            )
            Dim("Tap the panel to reconfigure everything live.")
        }
    }
}

@Composable
private fun <T : Enum<T>> ModePicker(entries: List<T>, selected: T, onSelect: (T) -> Unit) {
    var open by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { open = !open }
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            PickerText(selected.label())
            PickerText(if (open) "^" else "v")
        }
        if (open) {
            entries.forEach { entry ->
                PickerText(
                    text = entry.label(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF0B1218))
                        .clickable {
                            onSelect(entry)
                            open = false
                        }
                        .padding(14.dp),
                )
            }
        }
    }
}

private fun Enum<*>.label() = name.replace(Regex("(?<=[a-z])(?=[A-Z])"), " ").uppercase()

@Composable
private fun PickerText(text: String, modifier: Modifier = Modifier) {
    BasicText(
        text = text,
        modifier = modifier,
        style = TextStyle(color = Color(0xFFE8FDFF), fontSize = 15.sp),
    )
}

@Composable
private fun Dim(text: String) {
    BasicText(
        text = text,
        style = TextStyle(color = Color(0xFF7FB8C4), fontSize = 13.sp),
    )
}
