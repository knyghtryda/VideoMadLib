package com.yancorp.videomadlib

import android.app.Activity
import android.content.Context
import android.content.ComponentName
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.support.v7.app.AppCompatActivity
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util

import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v4.media.MediaDescriptionCompat
import android.view.View


//import kotlinx.android.synthetic.main.

class MainActivity : AppCompatActivity() {
    val READ_REQ = 24
    val WRITE_REQ = 25
    val EDIT_REQ = 26
    val DELETE_REQ = 27
    val VIDEO_CAPTURE = 101

    private var exoplayerView : PlayerView? = null
    private var exoplayer : SimpleExoPlayer? = null
    private var playbackStateBuilder : PlaybackStateCompat.Builder? = null
    private var mediaSession: MediaSessionCompat? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        */
        exoplayerView = findViewById(R.id.videoView)
        initializePlayer()
    }


    private fun initializePlayer() {
        val trackSelector = DefaultTrackSelector()
        exoplayer = ExoPlayerFactory.newSimpleInstance(baseContext, trackSelector)
        exoplayerView?.player = exoplayer

        val userAgent = Util.getUserAgent(baseContext, "Exo")
        val mediaUri = Uri.parse("asset:///1b.mp4")
        val uri1 = Uri.parse("asset:///1b.mp4")
        val uri2 = Uri.parse("asset:///2a.mp4")
        val uri3 = Uri.parse("asset:///3d.mp4")
        //val media1 = ExtractorMediaSource.Factory(ctx, )
        val mediaSource = ExtractorMediaSource(mediaUri, DefaultDataSourceFactory(baseContext, userAgent), DefaultExtractorsFactory(), null, null)
        val concatMediaSource = ConcatenatingMediaSource()
        //exoplayer?.prepare(mediaSource)
        exoplayer?.prepare(buildMediaSource())
        val componentName = ComponentName(baseContext, "Exo")
        mediaSession = MediaSessionCompat(baseContext, "ExoPlayer", componentName, null)

        playbackStateBuilder = PlaybackStateCompat.Builder()

        playbackStateBuilder?.setActions(PlaybackStateCompat.ACTION_PLAY or PlaybackStateCompat.ACTION_PAUSE or
                PlaybackStateCompat.ACTION_FAST_FORWARD)

        mediaSession?.setPlaybackState(playbackStateBuilder?.build())
        mediaSession?.isActive = true
    }

    fun buildMediaSource(): MediaSource {
        val uriList = mutableListOf<MediaSource>()
        MediaLibrary.list.forEach {
            uriList.add(createExtractorMediaSource(it.mediaUri!!))
        }
        return ConcatenatingMediaSource(*uriList.toTypedArray())
    }

    private fun createExtractorMediaSource(uri: Uri): MediaSource {
        val userAgent = Util.getUserAgent(baseContext, "Exo")
        return ExtractorMediaSource.Factory(DefaultDataSourceFactory(baseContext, userAgent)).createMediaSource(uri)
    }

    override fun onActivityResult(requestCode: Int , resultCode: Int, resultData: Intent?) {

        if (resultCode == Activity.RESULT_OK) {
            if(requestCode == READ_REQ || requestCode == VIDEO_CAPTURE){
                MediaLibrary.list.removeAt(0)
                MediaLibrary.list.add(0, with(MediaDescriptionCompat.Builder()) {
                    // License - https://peach.blender.org/download/
                    setMediaUri(resultData?.data!!)
                    //setMediaUri(Uri.parse("http://download.blender.org/peach/bigbuckbunny_movies/BigBuckBunny_320x180.mp4"))
                    build()
                })
                exoplayer?.prepare(buildMediaSource())
            }
        }
    }

    fun randomize(view: View) {
        MediaLibrary.list.clear()
        MediaLibrary.list.add(
                with(MediaDescriptionCompat.Builder()) {
                    setMediaUri(Uri.parse("asset:///a" + (1..2).shuffled().first() + ".mp4"))
                    build()
                })
        MediaLibrary.list.add(
                with(MediaDescriptionCompat.Builder()) {
                    setMediaUri(Uri.parse("asset:///b" + (1..2).shuffled().first() + ".mp4"))
                    build()
                })
        MediaLibrary.list.add(
                with(MediaDescriptionCompat.Builder()) {
                    setMediaUri(Uri.parse("asset:///c" + (1..2).shuffled().first() + ".mp4"))
                    build()
                })
        exoplayer?.prepare(buildMediaSource())
    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
    }

    private fun releasePlayer() {
        if (exoplayer != null) {
            exoplayer?.stop()
            exoplayer?.release()
            exoplayer = null
        }
    }

    fun readFile(view: View) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "video/*"

        startActivityForResult(intent, READ_REQ)
    }

    fun recordVideo(view: View) {
        val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        startActivityForResult(intent, VIDEO_CAPTURE)
    }

    private fun hasCamera(): Boolean {
        return packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            hideSystemUI()
        }
    }

    private fun hideSystemUI() {
        val decorView = window.decorView
        decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }
}
