package com.example.jaldrishti20

import android.Manifest
import android.content.pm.PackageManager
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.ar.core.Anchor
import com.google.ar.core.Config
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.core.Session
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class ARPreviewActivity : AppCompatActivity(), GLSurfaceView.Renderer {
    private var mSession: Session? = null
    private lateinit var surfaceView: GLSurfaceView
    private val anchors = mutableListOf<Anchor>() // 3D model ki locations save karne ke liye

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ar_preview)

        surfaceView = findViewById(R.id.ar_surface_view)
        surfaceView.preserveEGLContextOnPause = true
        surfaceView.setEGLContextClientVersion(2)
        surfaceView.setRenderer(this)
        surfaceView.renderMode = GLSurfaceView.RENDERMODE_CONTINUOUSLY

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 101)
        } else {
            resumeARSession()
        }
    }

    private fun resumeARSession() {
        if (mSession == null) {
            try {
                mSession = Session(this)
                val config = Config(mSession)
                // Surface detection enable karna zaroori hai
                config.planeFindingMode = Config.PlaneFindingMode.HORIZONTAL
                mSession?.configure(config)
            } catch (e: Exception) {
                Toast.makeText(this, "AR Error: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                finish()
                return
            }
        }
        try { mSession?.resume() } catch (e: Exception) { finish() }
    }

    // Screen tap detect karke model place karna
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP && mSession != null) {
            val frame = mSession?.update()
            // Check kar rahe hain ki jahan tap kiya wahan koi real surface hai?
            val hits = frame?.hitTest(event.x, event.y)
            for (hit in hits ?: emptyList<HitResult>()) {
                val trackable = hit.trackable
                if (trackable is Plane && trackable.isPoseInPolygon(hit.hitPose)) {
                    // Yahan hum Anchor bana rahe hain (Isi par 3D model dikhega)
                    anchors.add(hit.createAnchor())
                    Toast.makeText(this, "Rainwater Pit Placed!", Toast.LENGTH_SHORT).show()
                    break
                }
            }
        }
        return super.onTouchEvent(event)
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        GLES20.glClearColor(0f, 0f, 0f, 1.0f)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
        mSession?.setDisplayGeometry(0, width, height)
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BUFFER_BIT)
        try {
            mSession?.update()
            // Yahan par aapka model rendering code aayega (OpenGL shaders ke saath)
        } catch (e: Exception) {}
    }

    override fun onPause() {
        super.onPause()
        surfaceView.onPause()
        mSession?.pause()
    }

    override fun onResume() {
        super.onResume()
        surfaceView.onResume()
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED) resumeARSession()
    }
}