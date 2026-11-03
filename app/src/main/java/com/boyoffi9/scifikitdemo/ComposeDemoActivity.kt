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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF05080C))
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        GlitchText(text = "SCIFIKIT // COMPOSE", fontSize = 30.sp, replayKey = replay)
        Dim("Tap any glitch text to glitch it.")

        LiveConfig()

        HudFrame(modifier = Modifier.fillMaxWidth()) {
            Column {
                GlitchText(text = "SYSTEM ONLINE", fontSize = 22.sp, replayKey = replay)
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
                intensity = 1.6f,
                glitchIntervalMs = 1800L,
                decodeDurationMs = 1400L,
                replayKey = replay,
            )
        }

        HudFrame(
            modifier = Modifier.fillMaxWidth(),
            accentColor = Color(0xFFB388FF),
            sweep = false,
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                DecodeModes.forEach { (label, mode) ->
                    GlitchText(text = label, fontSize = 16.sp, decode = mode.config, replayKey = replay)
                }
            }
        }

        HudFrame(
            modifier = Modifier.fillMaxWidth(),
            accentColor = Color(0xFF7CFFB2),
            sweep = false,
        ) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                HoloRadar(accentColor = Color(0xFF7CFFB2))
            }
        }

        HudFrame(
            modifier = Modifier.fillMaxWidth(),
            accentColor = Color(0xFFFF2D6F),
            sweep = false,
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                GlitchModes.forEach { (label, mode) ->
                    GlitchText(text = label, fontSize = 16.sp, glitch = mode.config)
                }
            }
        }

        HudFrame(
            modifier = Modifier.fillMaxWidth(),
            accentColor = Color(0xFF7CFFB2),
            sweep = false,
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                TouchModes.forEach { (label, mode) ->
                    GlitchText(text = label, fontSize = 22.sp, touch = mode.config)
                }
            }
        }

        HudFrame(
            modifier = Modifier.fillMaxWidth(),
            sweep = false,
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                RadarModes.chunked(2).forEach { row ->
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                        row.forEach { (label, mode, color) ->
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                HoloRadar(size = 140.dp, accentColor = color, radar = mode.config)
                                Dim(label)
                            }
                        }
                    }
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
            GlitchText(text = "REPLAY DECODE >", fontSize = 18.sp)
        }

        HudFrame(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onBack),
            accentColor = Color(0xFF7CFFB2),
            sweep = false,
        ) {
            GlitchText(text = "< BACK TO VIEWS DEMO", fontSize = 18.sp)
        }
    }
}

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

private val GlitchModes = listOf(
    "SUBTLE // BARELY THERE" to GlitchMode.Subtle,
    "STANDARD // THE ORIGINAL" to GlitchMode.Standard,
    "VIOLENT // FULL TEAR" to GlitchMode.Violent,
    "SIGNAL LOSS // DROPS OUT" to GlitchMode.SignalLoss,
)

private val TouchModes = listOf(
    "BURST // TAP" to TouchMode.Burst,
    "FOLLOW // DRAG ON ME" to TouchMode.Follow,
    "CHARGE // HOLD ME" to TouchMode.Charge,
    "FOLLOW + CHARGE // BOTH" to TouchMode.FollowCharge,
)

private val RadarModes = listOf(
    Triple("TACTICAL", RadarMode.Tactical, Color(0xFF7CFFB2)),
    Triple("CYBERPUNK", RadarMode.Cyberpunk, Color(0xFF00E5FF)),
    Triple("PHOSPHOR", RadarMode.Phosphor, Color(0xFF7CFFB2)),
    Triple("HOLOGRAM", RadarMode.Hologram, Color(0xFF00E5FF)),
)

private val DecodeModes = listOf(
    "CLASSIC // THE ORIGINAL" to DecodeMode.Classic,
    "RELAXED // SLOW AND CALM" to DecodeMode.Relaxed,
    "ACTIVE // NEVER SETTLES" to DecodeMode.Active,
    "RANDOM // ANY ORDER" to DecodeMode.Random,
    "CENTER OUT // FROM MIDDLE" to DecodeMode.CenterOut,
    "TYPEWRITER // CURSOR" to DecodeMode.Typewriter,
)

@Composable
private fun Dim(text: String) {
    BasicText(
        text = text,
        style = TextStyle(color = Color(0xFF7FB8C4), fontSize = 13.sp),
    )
}
