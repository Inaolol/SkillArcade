package com.example.skillarcade.ui.video

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class YouTubeVideoTest {

    @Test
    fun `extractYouTubeVideoId reads watch URLs`() {
        assertEquals(
            "dQw4w9WgXcQ",
            extractYouTubeVideoId("https://www.youtube.com/watch?v=dQw4w9WgXcQ&t=42s")
        )
    }

    @Test
    fun `extractYouTubeVideoId reads short URLs`() {
        assertEquals(
            "dQw4w9WgXcQ",
            extractYouTubeVideoId("https://youtu.be/dQw4w9WgXcQ?si=abc123")
        )
    }

    @Test
    fun `extractYouTubeVideoId reads embed URLs`() {
        assertEquals(
            "dQw4w9WgXcQ",
            extractYouTubeVideoId("https://www.youtube.com/embed/dQw4w9WgXcQ")
        )
    }

    @Test
    fun `extractYouTubeVideoId reads shorts URLs`() {
        assertEquals(
            "dQw4w9WgXcQ",
            extractYouTubeVideoId("https://youtube.com/shorts/dQw4w9WgXcQ")
        )
    }

    @Test
    fun `extractYouTubeVideoId returns null for unsupported URLs`() {
        assertNull(extractYouTubeVideoId("https://example.com/watch?v=dQw4w9WgXcQ"))
        assertNull(extractYouTubeVideoId("not a url"))
        assertNull(extractYouTubeVideoId(""))
    }

    @Test
    fun `buildYouTubeEmbedUrl appends inline playback parameters`() {
        assertEquals(
            "https://www.youtube.com/embed/dQw4w9WgXcQ?playsinline=1&rel=0&enablejsapi=1&origin=https%3A%2F%2Fskillarcade.local",
            buildYouTubeEmbedUrl("dQw4w9WgXcQ")
        )
    }

    @Test
    fun `buildYouTubePlayerHtml wraps embed in full size black iframe page`() {
        val html = buildYouTubePlayerHtml("dQw4w9WgXcQ")

        assertTrue(html.contains("<!doctype html>"))
        assertTrue(html.contains("background: #000000"))
        assertTrue(html.contains("width: 100%"))
        assertTrue(html.contains("height: 100%"))
        assertTrue(html.contains("src=\"${buildYouTubeEmbedUrl("dQw4w9WgXcQ")}\""))
        assertTrue(html.contains("allowfullscreen"))
        assertTrue(html.contains("https://www.youtube.com/iframe_api"))
        assertTrue(html.contains("skillarcade://player-ready"))
        assertTrue(html.contains("skillarcade://player-error"))
    }
}
