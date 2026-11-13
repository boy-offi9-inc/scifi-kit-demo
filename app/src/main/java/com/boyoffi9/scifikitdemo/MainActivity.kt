package com.boyoffi9.scifikitdemo

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.boyoffi9.scifikit.GlitchTextView
import com.boyoffi9.scifikit.HoloRadarView
import com.boyoffi9.scifikit.HudFrameView
import com.boyoffi9.scifikit.NeonButtonView
import com.boyoffi9.scifikit.core.DecodeMode
import com.boyoffi9.scifikit.core.GlitchMode
import com.boyoffi9.scifikit.core.NeonStyle
import com.boyoffi9.scifikit.core.RadarMode
import com.boyoffi9.scifikit.core.SciFiPalette
import com.boyoffi9.scifikit.core.TouchMode
import com.boyoffi9.scifikit.sciFiPalette

class MainActivity : ComponentActivity() {

    private class Page(val title: String, val layout: Int)

    private val pages = listOf(
        Page("HUD", R.layout.page_hud),
        Page("TEXT", R.layout.page_text),
        Page("RADAR", R.layout.page_radar),
        Page("BUTTON", R.layout.page_button),
        Page("THEME", R.layout.page_theme),
        Page("LIVE", R.layout.page_live),
        Page("ALL", R.layout.page_all),
    )

    private val overlays = intArrayOf(
        0,
        R.style.ThemeOverlay_SciFi_Amber,
        R.style.ThemeOverlay_SciFi_Phosphor,
        R.style.ThemeOverlay_SciFi_Magenta,
        R.style.ThemeOverlay_SciFi_Mono,
    )

    private val themeNames = listOf("THEME: CYAN", "THEME: AMBER", "THEME: PHOSPHOR", "THEME: MAGENTA", "THEME: MONO")

    private val liveColors = intArrayOf(
        0xFF00E5FF.toInt(),
        0xFFFF2D6F.toInt(),
        0xFFFFC400.toInt(),
        0xFF7CFFB2.toInt(),
        0xFFB388FF.toInt(),
    )

    private val tabViews = mutableListOf<TextView>()
    private lateinit var palette: SciFiPalette

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bars = SystemBarStyle.dark(Color.TRANSPARENT)
        enableEdgeToEdge(statusBarStyle = bars, navigationBarStyle = bars)
        if (overlays[overlayIndex] != 0) theme.applyStyle(overlays[overlayIndex], true)
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.root)) { view, insets ->
            val safe = insets.getInsets(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout())
            view.setPadding(safe.left, safe.top, safe.right, safe.bottom)
            insets
        }
        palette = sciFiPalette()
        buildTabs()
        show(currentPage)
    }

    private fun buildTabs() {
        val tabs = findViewById<LinearLayout>(R.id.tabs)
        val density = resources.displayMetrics.density
        val pad = (8 * density).toInt()
        pages.forEachIndexed { index, page ->
            tabs.addView(tab(page.title, pad) { show(index) }.also { tabViews += it })
        }
        tabs.addView(tab("COMPOSE >", pad) { startActivity(Intent(this, ComposeDemoActivity::class.java)) })
    }

    private fun tab(title: String, pad: Int, onClick: () -> Unit): TextView = TextView(this).apply {
        text = title
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
        letterSpacing = 0.1f
        gravity = Gravity.CENTER
        setPadding(pad * 2, pad * 2, pad * 2, pad * 2)
        setTextColor(palette.dim)
        setOnClickListener { onClick() }
    }

    private fun show(index: Int) {
        currentPage = index
        tabViews.forEachIndexed { i, view -> view.setTextColor(if (i == index) palette.accent else palette.dim) }
        val container = findViewById<FrameLayout>(R.id.pageContainer)
        container.removeAllViews()
        val root = layoutInflater.inflate(pages[index].layout, container, false)
        container.addView(root)
        bindText(root)
        bindRadar(root)
        bindButton(root)
        bindTheme(root)
        bindLive(root)
    }

    private fun bindText(root: View) {
        val decodeSample = root.findViewById<GlitchTextView>(R.id.decodeSample) ?: return
        val glitchSample = root.findViewById<GlitchTextView>(R.id.glitchSample)
        val touchSample = root.findViewById<GlitchTextView>(R.id.touchSample)

        root.findViewById<View>(R.id.replayDecode)?.setOnClickListener { decodeSample.replay() }
        bindPicker(root, R.id.decodePicker, DecodeMode.entries) { decodeSample.decode = it.config }
        bindPicker(root, R.id.glitchPicker, GlitchMode.entries) { glitchSample?.glitch = it.config }
        bindPicker(root, R.id.touchPicker, TouchMode.entries) { touchSample?.touch = it.config }
    }

    private fun bindRadar(root: View) {
        val sample = root.findViewById<HoloRadarView>(R.id.radarSample) ?: return
        bindPicker(root, R.id.radarPicker, RadarMode.entries) { sample.radar = it.config }
    }

    private fun bindButton(root: View) {
        val sample = root.findViewById<NeonButtonView>(R.id.neonSample) ?: return
        val count = root.findViewById<TextView>(R.id.neonCount)
        var taps = 0
        sample.setOnClickListener {
            taps++
            count?.text = "TAPS: $taps"
        }
        bindPicker(root, R.id.neonPicker, NeonStyle.entries) { sample.neon = it.config }
    }

    private fun bindTheme(root: View) {
        val spinner = root.findViewById<Spinner>(R.id.themePicker) ?: return
        val adapter = ArrayAdapter(this, R.layout.spinner_item, themeNames)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.setSelection(overlayIndex, false)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, itemId: Long) {
                if (position == overlayIndex) return
                overlayIndex = position
                recreate()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
        }
    }

    private fun <T : Enum<T>> bindPicker(root: View, id: Int, modes: List<T>, onPick: (T) -> Unit) {
        val spinner = root.findViewById<Spinner>(id) ?: return
        val names = modes.map { it.name.replace(Regex("(?<=[a-z])(?=[A-Z])"), " ").uppercase() }
        val adapter = ArrayAdapter(this, R.layout.spinner_item, names)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, itemId: Long) {
                onPick(modes[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
        }
    }

    private fun bindLive(root: View) {
        val frame = root.findViewById<HudFrameView>(R.id.liveFrame) ?: return
        val label = root.findViewById<GlitchTextView>(R.id.liveText) ?: return
        val radar = root.findViewById<HoloRadarView>(R.id.liveRadar) ?: return
        val density = resources.displayMetrics.density
        var step = 0

        frame.setOnClickListener {
            step++
            val color = liveColors[step % liveColors.size]
            val mode = DecodeMode.entries[step % DecodeMode.entries.size]

            frame.accentColor = color
            frame.fillColor = (color and 0x00FFFFFF) or 0x14000000
            frame.scanlines = step % 2 == 0
            frame.chamferPx = (10 + step % 4 * 6) * density

            label.splitColorA = liveColors[(step + 1) % liveColors.size]
            label.text = "LIVE // ${mode.name.uppercase()}"
            label.decode = mode.config
            label.glitch = GlitchMode.entries[step % GlitchMode.entries.size].config

            radar.accentColor = color
            radar.radar = RadarMode.entries[step % RadarMode.entries.size].config
            radar.rings = 2 + step % 5
            radar.rotationPeriodMs = if (step % 2 == 0) 4000L else 1800L
        }
    }

    private companion object {
        var overlayIndex = 0
        var currentPage = 0
    }
}
