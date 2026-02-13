package com.example.jaldrishti20.ui.theme

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast

class ARPreviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Verifying with the Tiger first - if this works, the problem is solved!
        val modelUrl = "https://storage.googleapis.com/rainwater-harvesting/rain_water_collector_idea_model%20(1)%20(1).glb"

        try {
            val sceneViewerIntent = Intent(Intent.ACTION_VIEW)

            // USE "ar_preferred" instead of "ar_only"
            // This allows the app to show a 3D preview if the AR camera has a permission glitch
            val intentUri = Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                .appendQueryParameter("file", modelUrl)
                .appendQueryParameter("mode", "ar_preferred")
                .appendQueryParameter("title", "Rainwater Collector")
                .build()

            sceneViewerIntent.data = intentUri

            // CRITICAL: Ensure this is exactly "com.google.android.googlequicksearchbox"
            sceneViewerIntent.setPackage("com.google.android.googlequicksearchbox")

            startActivity(sceneViewerIntent)
            finish()
        } catch (e: Exception) {
            // If the Google app is missing, try a browser fallback
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://arvr.google.com/scene-viewer/1.0?file=$modelUrl"))
                startActivity(intent)
            } catch (e2: Exception) {
                Toast.makeText(this, "Please ensure the Google app is enabled", Toast.LENGTH_LONG).show()
            }
            finish()
        }
    }
}
