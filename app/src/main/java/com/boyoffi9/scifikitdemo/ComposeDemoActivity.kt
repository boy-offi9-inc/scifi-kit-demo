package com.boyoffi9.scifikitdemo

import android.graphics.Color as AndroidColor
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
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
import com.boyoffi9.scifikit.compose.NeonAlert
import com.boyoffi9.scifikit.compose.NeonBadge
import com.boyoffi9.scifikit.compose.NeonButton
import com.boyoffi9.scifikit.compose.NeonGauge
import com.boyoffi9.scifikit.compose.NeonProgress
import com.boyoffi9.scifikit.compose.SciFiCard
import com.boyoffi9.scifikit.compose.SciFiTheme
import com.boyoffi9.scifikit.core.DecodeMode
import com.boyoffi9.scifikit.core.GlitchMode
import com.boyoffi9.scifikit.core.NeonAlertPainter.Severity
import com.boyoffi9.scifikit.core.NeonBadgePainter.State
import com.boyoffi9.scifikit.core.NeonGaugePainter.Readout
import com.boyoffi9.scifikit.core.CardStyle
import com.boyoffi9.scifikit.core.NeonStyle
import com.boyoffi9.scifikit.core.RadarMode
import com.boyoffi9.scifikit.core.SciFiPalette
import com.boyoffi9.scifikit.core.TouchMode

/** Jetpack Compose demo. Same look as the Views demo, same painters underneath. */
class ComposeDemoActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bars = SystemBarStyle.dark(AndroidColor.TRANSPARENT)
        enableEdgeToEdge(statusBarStyle = bars, navigationBarStyle = bars)
        setContent { DemoRoot(onBack = { finish() }) }
    }
}

private enum class DemoPalette(val palette: SciFiPalette) {
    Cyan(SciFiPalette()),
    Amber(
        SciFiPalette(
            accent = 0xFFFFC400.toInt(),
            secondary = 0xFFFF5A1F.toInt(),
            text = 0xFFFFF1CC.toInt(),
            dim = 0xFFC9A24A.toInt(),
            background = 0xFF0A0700.toInt(),
            glow = 1.3f,
        ),
    ),
    Phosphor(
        SciFiPalette(
            accent = 0xFF7CFFB2.toInt(),
            secondary = 0xFFC8FF4D.toInt(),
            text = 0xFFD9FFE8.toInt(),
            dim = 0xFF5FA37A.toInt(),
            background = 0xFF03100A.toInt(),
            glow = 1.1f,
        ),
    ),
    Magenta(
        SciFiPalette(
            accent = 0xFFFF2D6F.toInt(),
            secondary = 0xFF00E5FF.toInt(),
            text = 0xFFFFE3EE.toInt(),
            dim = 0xFFC27A94.toInt(),
            background = 0xFF0E0510.toInt(),
            glow = 1.2f,
        ),
    ),
    Mono(
        SciFiPalette(
            accent = 0xFFE0E0E0.toInt(),
            secondary = 0xFF8A8A8A.toInt(),
            text = 0xFFFFFFFF.toInt(),
            dim = 0xFF888888.toInt(),
            background = 0xFF000000.toInt(),
            glow = 0f,
        ),
    ),
}

private enum class DemoPage(val title: String) {
    Hud("HUD"),
    Text("TEXT"),
    Radar("RADAR"),
    Button("BUTTON"),
    Card("CARD"),
    Status("STATUS"),
    Theme("THEME"),
    Live("LIVE"),
    All("ALL"),
}

@Composable
private fun DemoRoot(onBack: () -> Unit) {
    var selected by remember { mutableStateOf(DemoPalette.Cyan) }
    var page by remember { mutableStateOf(DemoPage.Hud) }

    SciFiTheme(palette = selected.palette) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(SciFiTheme.palette.background))
                .safeDrawingPadding(),
        ) {
            GlitchText(
                text = "SCIFIKIT // COMPOSE",
                modifier = Modifier.padding(start = 20.dp, top = 20.dp),
                fontSize = 24.sp,
                glitch = Calm,
            )
            Dim(
                "Neon HUD components for Views and Compose",
                Modifier.padding(start = 20.dp, top = 2.dp),
            )
            Box(
                modifier = Modifier
                    .padding(start = 20.dp, top = 12.dp, end = 20.dp)
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(SciFiTheme.palette.dim).copy(alpha = 0.25f)),
            )
            Tabs(page, onSelect = { page = it }, onBack = onBack)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                when (page) {
                    DemoPage.Hud -> HudSection()
                    DemoPage.Text -> TextSection()
                    DemoPage.Radar -> RadarSection()
                    DemoPage.Button -> ButtonSection()
                    DemoPage.Card -> CardSection()
                    DemoPage.Status -> StatusSection()
                    DemoPage.Theme -> ThemeSection(selected) { selected = it }
                    DemoPage.Live -> LiveConfig()
                    DemoPage.All -> {
                        HudSection()
                        TextSection()
                        RadarSection()
                        ButtonSection()
                        CardSection()
                        StatusSection()
                        ThemeSection(selected) { selected = it }
                        LiveConfig()
                    }
                }
            }
        }
    }
}

@Composable
private fun Tabs(selected: DemoPage, onSelect: (DemoPage) -> Unit, onBack: () -> Unit) {
    val palette = SciFiTheme.palette
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 12.dp),
    ) {
        TabText("< VIEWS", false, Color(palette.dim), onBack)
        DemoPage.entries.forEach { entry ->
            TabText(entry.title, entry == selected, Color(if (entry == selected) palette.accent else palette.dim)) {
                onSelect(entry)
            }
        }
    }
}

@Composable
private fun TabText(text: String, selected: Boolean, color: Color, onClick: () -> Unit) {
    Column(modifier = Modifier.clickable(onClick = onClick)) {
        BasicText(
            text = text,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 8.dp),
            style = TextStyle(color = color, fontSize = 14.sp, letterSpacing = 1.4.sp),
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(if (selected) Color(SciFiTheme.palette.accent) else Color.Transparent),
        )
    }
}

@Composable
private fun SectionLabel(text: String) {
    BasicText(
        text = text.uppercase(),
        style = TextStyle(
            color = Color(SciFiTheme.palette.dim),
            fontSize = 11.sp,
            letterSpacing = 2.sp,
        ),
    )
}

@Composable
private fun HudSection() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        SectionLabel("HUD frames")
        HudFrame(modifier = Modifier.fillMaxWidth()) {
            Column {
                GlitchText(text = "SYSTEM ONLINE", fontSize = 22.sp, glitch = Calm)
                Dim("Default frame. Takes its colour from the theme.")
            }
        }
        HudFrame(
            modifier = Modifier.fillMaxWidth(),
            accentColor = Color(0xFFFF2D6F),
            chamfer = 22.dp,
        ) {
            Column {
                GlitchText(
                    text = "INTRUSION DETECTED",
                    fontSize = 22.sp,
                    splitColorA = Color(0xFF00E5FF),
                    splitColorB = Color(0xFFFFC400),
                    glitch = Calm,
                    intensity = 1.6f,
                )
                Dim("chamfer = 22.dp, accent #FF2D6F")
            }
        }
        HudFrame(
            modifier = Modifier.fillMaxWidth(),
            accentColor = Color(0xFFB388FF),
            sweep = false,
        ) {
            Column {
                GlitchText(text = "STATIC FRAME", fontSize = 22.sp, glitch = Calm)
                Dim("sweep = false")
            }
        }
        HudFrame(
            modifier = Modifier.fillMaxWidth(),
            accentColor = Color(0xFF7CFFB2),
            scanlines = false,
            sweep = false,
        ) {
            Column {
                GlitchText(text = "CLEAN FRAME", fontSize = 22.sp, glitch = Calm)
                Dim("scanlines = false")
            }
        }
    }
}

@Composable
private fun TextSection() {
    var replay by remember { mutableIntStateOf(0) }
    var decode by remember { mutableStateOf(DecodeMode.Classic) }
    var glitch by remember { mutableStateOf(GlitchMode.Standard) }
    var touch by remember { mutableStateOf(TouchMode.Burst) }

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        SectionLabel("Glitch text")
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
            modifier = Modifier
                .fillMaxWidth()
                .clickable { replay++ },
            accentColor = Color(0xFFFFC400),
            sweep = false,
        ) {
            GlitchText(text = "REPLAY DECODE >", fontSize = 18.sp, glitch = Calm)
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
    }
}

@Composable
private fun RadarSection() {
    var radar by remember { mutableStateOf(RadarMode.Classic) }

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        SectionLabel("Holo radar")
        HudFrame(
        modifier = Modifier.fillMaxWidth(),
        accentColor = Color(0xFF00E5FF),
        sweep = false,
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            ModePicker(RadarMode.entries, radar) { radar = it }
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                HoloRadar(size = 180.dp, accentColor = Color(0xFF00E5FF), radar = radar.config)
            }
        }
    }
    }
}

@Composable
private fun ButtonSection() {
    var style by remember { mutableStateOf(NeonStyle.Outline) }
    var taps by remember { mutableIntStateOf(0) }

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        SectionLabel("Neon buttons")
        HudFrame(
        modifier = Modifier.fillMaxWidth(),
        accentColor = Color(0xFF00E5FF),
        sweep = false,
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            ModePicker(NeonStyle.entries, style) { style = it }
            NeonButton(
                text = "ENGAGE",
                onClick = { taps++ },
                modifier = Modifier.fillMaxWidth(),
                style = style.config,
            )
            NeonButton(
                text = "LOCKED",
                onClick = { },
                modifier = Modifier.fillMaxWidth(),
                enabled = false,
                style = style.config,
            )
            Dim("TAPS: $taps")
        }
    }
    }
}


@Composable
private fun CardSection() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        SectionLabel("Sci-fi card")
        SciFiCard(
            modifier = Modifier.fillMaxWidth(),
            style = CardStyle.Panel.config.copy(march = 1f),
            accentColor = Color(0xFFFFC400),
        ) {
            Column(modifier = Modifier.padding(4.dp)) {
                BasicText(
                    "NEW UPDATE",
                    style = TextStyle(color = Color(0xFFFFC400), fontSize = 14.sp),
                )
                Dim(
                    "The panel style: cut corners, layered border, accent bands and marching hazard stripes. Put any composables inside.",
                    Modifier.padding(top = 6.dp),
                )
            }
        }
        SciFiCard(
            modifier = Modifier.fillMaxWidth(),
            style = CardStyle.Outline.config.copy(grid = 0.12f),
        ) {
            Column(modifier = Modifier.padding(4.dp)) {
                BasicText(
                    "OUTLINE // TARGET LOCKED",
                    style = TextStyle(color = Color(SciFiTheme.palette.accent), fontSize = 14.sp),
                )
                Dim(
                    "Thin frame with layered border, corner brackets, a target ring, arrows, dotted rows, a hazard footer and a faint grid.",
                    Modifier.padding(top = 6.dp),
                )
            }
        }
        SciFiCard(
            modifier = Modifier.fillMaxWidth(),
            style = CardStyle.Targeted.config,
            accentColor = Color(0xFFFF3B5C),
        ) {
            Column(modifier = Modifier.padding(4.dp)) {
                BasicText(
                    "TARGETED // TRACKING",
                    style = TextStyle(color = Color(0xFFFF3B5C), fontSize = 14.sp),
                )
                Dim(
                    "Flat-top frame with bracket corners, side ticks, a top hazard strip and a marching target ring. Built for hostile and lock-on states.",
                    Modifier.padding(top = 6.dp),
                )
            }
        }
        SciFiCard(
            modifier = Modifier.fillMaxWidth(),
            style = CardStyle.Terminal.config,
            accentColor = Color(0xFF3BFFA0),
        ) {
            Column(modifier = Modifier.padding(4.dp)) {
                BasicText(
                    "TERMINAL // LOG OUTPUT",
                    style = TextStyle(color = Color(0xFF3BFFA0), fontSize = 14.sp),
                )
                Dim(
                    "Sweep-cut corner, layered inner line, dot rows and edge arrows. A calmer console frame for feed and log content.",
                    Modifier.padding(top = 6.dp),
                )
            }
        }
        SciFiCard(modifier = Modifier.fillMaxWidth(), style = CardStyle.Plain.config) {
            Dim("PLAIN // QUIET CONTAINER", Modifier.padding(4.dp))
        }
    }
}

@Composable
private fun StatusSection() {
    var load by remember { mutableFloatStateOf(0.842f) }

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        SectionLabel("Neon progress")
        HudFrame(modifier = Modifier.fillMaxWidth(), sweep = false) {
            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                NeonProgress(label = "SYS. CORE LOAD", progress = load)
                NeonProgress(label = "MEM. ALLOCATION", progress = 0.699f)
                NeonProgress(label = "NET UPLINK", progress = 0.918f)
                NeonProgress(label = "HULL INTEGRITY", progress = 0.78f, showPercent = false)
                NeonProgress(label = "REACTOR OUTPUT", progress = 0.46f, segmented = true)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf(0.2f, 0.55f, 0.842f, 0.95f).forEach { p ->
                        PickerText(
                            text = "${(p * 100).toInt()}%",
                            modifier = Modifier
                                .background(Color(0xFF0B1218))
                                .clickable { load = p }
                                .padding(10.dp),
                        )
                    }
                }
            }
        }

        SectionLabel("Neon gauges")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            NeonGauge(progress = 0.72f, modifier = Modifier.size(120.dp), readout = Readout.Percent)
            NeonGauge(
                progress = 130f / 180f,
                value = 130f,
                unit = "KM/H",
                readout = Readout.Value,
                sweepDegrees = 300f,
                ticks = 10,
                modifier = Modifier.size(140.dp),
            )
        }

        SectionLabel("Neon badges")
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            NeonBadge(text = "BETA", state = State.Normal, filled = true)
            NeonBadge(text = "NEW", state = State.Success, filled = true)
            NeonBadge(text = "PREMIUM", state = State.Warning, filled = true)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            NeonBadge(text = "ONLINE", state = State.Success, showDot = true)
            NeonBadge(text = "OFFLINE", state = State.Muted, showDot = true)
            NeonBadge(text = "DANGER", state = State.Danger, filled = true)
        }

        SectionLabel("Neon alerts")
        NeonAlert(text = "SCAN COMPLETE — NO HOSTILES DETECTED", severity = Severity.Success, showChevrons = true)
        NeonAlert(text = "SIGNAL STABLE", severity = Severity.Info, showChevrons = true)
        NeonAlert(text = "CORE TEMPERATURE HIGH", severity = Severity.Warning, hazard = 0.22f)
        NeonAlert(text = "REACTOR OVERLOAD IMMINENT", severity = Severity.Danger, hazard = 0.3f)
    }
}

@Composable
private fun ThemeSection(selected: DemoPalette, onSelect: (DemoPalette) -> Unit) {
    HudFrame(modifier = Modifier.fillMaxWidth(), sweep = false) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            SectionLabel("Theme overlays")
            ModePicker(DemoPalette.entries, selected, onSelect)
            GlitchText(text = "THEMED // FOLLOWS THE PALETTE", fontSize = 18.sp, glitch = Calm)
            HoloRadar(size = 140.dp)
            Dim("Every colour here comes from the theme.")
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
            SectionLabel("Status console")
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
        style = TextStyle(color = Color(SciFiTheme.palette.text), fontSize = 15.sp),
    )
}

@Composable
private fun Dim(text: String, modifier: Modifier = Modifier) {
    BasicText(
        text = text,
        modifier = modifier,
        style = TextStyle(color = Color(SciFiTheme.palette.dim), fontSize = 13.sp),
    )
}
