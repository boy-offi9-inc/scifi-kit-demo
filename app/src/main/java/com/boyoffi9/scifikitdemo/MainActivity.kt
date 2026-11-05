package com.boyoffi9.scifikitdemo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.ComponentActivity
import com.boyoffi9.scifikit.GlitchTextView
import com.boyoffi9.scifikit.HoloRadarView
import com.boyoffi9.scifikit.HudFrameView
import com.boyoffi9.scifikit.core.DecodeMode
import com.boyoffi9.scifikit.core.GlitchMode
import com.boyoffi9.scifikit.core.RadarMode
import com.boyoffi9.scifikit.core.TouchMode

class MainActivity : ComponentActivity() {

    private val liveColors = intArrayOf(
        0xFF00E5FF.toInt(),
        0xFFFF2D6F.toInt(),
        0xFFFFC400.toInt(),
        0xFF7CFFB2.toInt(),
        0xFFB388FF.toInt(),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpLiveConfig()
        setUpPickers()

        val decodeSample = findViewById<GlitchTextView>(R.id.decodeSample)
        findViewById<View>(R.id.replayDecode).setOnClickListener { decodeSample.replay() }
        findViewById<View>(R.id.openCompose).setOnClickListener {
            startActivity(Intent(this, ComposeDemoActivity::class.java))
        }
    }

    private fun setUpPickers() {
        val decodeSample = findViewById<GlitchTextView>(R.id.decodeSample)
        val glitchSample = findViewById<GlitchTextView>(R.id.glitchSample)
        val touchSample = findViewById<GlitchTextView>(R.id.touchSample)
        val radarSample = findViewById<HoloRadarView>(R.id.radarSample)

        bindPicker(R.id.decodePicker, DecodeMode.entries) { decodeSample.decode = it.config }
        bindPicker(R.id.glitchPicker, GlitchMode.entries) { glitchSample.glitch = it.config }
        bindPicker(R.id.touchPicker, TouchMode.entries) { touchSample.touch = it.config }
        bindPicker(R.id.radarPicker, RadarMode.entries) { radarSample.radar = it.config }
    }

    private fun <T : Enum<T>> bindPicker(id: Int, modes: List<T>, onPick: (T) -> Unit) {
        val spinner = findViewById<Spinner>(id)
        val adapter = ArrayAdapter(this, R.layout.spinner_item, modes.map { it.name.replace(Regex("(?<=[a-z])(?=[A-Z])"), " ").uppercase() })
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, itemId: Long) {
                onPick(modes[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
        }
    }

    private fun setUpLiveConfig() {
        val frame = findViewById<HudFrameView>(R.id.liveFrame)
        val label = findViewById<GlitchTextView>(R.id.liveText)
        val radar = findViewById<HoloRadarView>(R.id.liveRadar)
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
}
