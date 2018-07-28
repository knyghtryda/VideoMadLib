package com.yancorp.videomadlib

import android.net.Uri
import android.support.v4.media.MediaDescriptionCompat

open class MediaLibrary(
        val list: MutableList<MediaDescriptionCompat>) :
        List<MediaDescriptionCompat> by list {
    companion object : MediaLibrary(mutableListOf())

    init {
        // More creative commons, creative commons videos - https://www.blender.org/about/projects/
        list.add(
                with(MediaDescriptionCompat.Builder()) {
                     setDescription("MP4 loaded over HTTP")
                    setMediaId("1")
                    // License - https://peach.blender.org/download/
                    setMediaUri(Uri.parse("asset:///1b.mp4"))
                    //setMediaUri(Uri.parse("http://download.blender.org/peach/bigbuckbunny_movies/BigBuckBunny_320x180.mp4"))
                    setTitle("Short film Big Buck Bunny")
                    setSubtitle("Streaming video")
                    build()
                })
        list.add(
                with(MediaDescriptionCompat.Builder()) {
                    setDescription("MP4 loaded over HTTP")
                    setMediaId("2")
                    // License - https://peach.blender.org/download/
                    setMediaUri(Uri.parse("asset:///2a.mp4"))
                    //setMediaUri(Uri.parse("http://download.blender.org/peach/bigbuckbunny_movies/BigBuckBunny_320x180.mp4"))
                    setTitle("Short film Big Buck Bunny")
                    setSubtitle("Streaming video")
                    build()
                })
        list.add(
                with(MediaDescriptionCompat.Builder()) {
                    setDescription("MP4 loaded over HTTP")
                    setMediaId("3")
                    // License - https://peach.blender.org/download/
                    setMediaUri(Uri.parse("asset:///3d.mp4"))
                    //setMediaUri(Uri.parse("http://download.blender.org/peach/bigbuckbunny_movies/BigBuckBunny_320x180.mp4"))
                    setTitle("Short film Big Buck Bunny")
                    setSubtitle("Streaming video")
                    build()
                })
    }
}