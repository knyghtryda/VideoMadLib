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
                    setMediaUri(Uri.parse("asset:///a1.mp4"))
                    build()
                })
        list.add(
                with(MediaDescriptionCompat.Builder()) {
                    setDescription("MP4 loaded over HTTP")
                    setMediaUri(Uri.parse("asset:///b1.mp4"))
                    build()
                })
        list.add(
                with(MediaDescriptionCompat.Builder()) {
                    setMediaUri(Uri.parse("asset:///c1.mp4"))
                    build()
                })
    }
}