package com.boyoffi9.scifikitdemo

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity

/** XML Views demo. The amber button opens the Compose demo. */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<android.view.View>(R.id.openCompose).setOnClickListener {
            startActivity(Intent(this, ComposeDemoActivity::class.java))
        }
    }
}
