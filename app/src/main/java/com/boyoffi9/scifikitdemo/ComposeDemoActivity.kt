package com.boyoffi9.scifikitdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
            accentColor = Color(0xFF7CFFB2),
            sweep = false,
        ) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                HoloRadar(accentColor = Color(0xFF7CFFB2))
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

@Composable
private fun Dim(text: String) {
    BasicText(
        text = text,
        style = TextStyle(color = Color(0xFF7FB8C4), fontSize = 13.sp),
    )
}
