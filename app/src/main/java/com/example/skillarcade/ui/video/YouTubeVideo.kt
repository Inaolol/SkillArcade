package com.example.skillarcade.ui.video

import java.net.URI
import java.net.URLDecoder

const val YOUTUBE_PLAYER_BASE_URL = "https://skillarcade.local"

private const val YOUTUBE_PLAYER_ORIGIN_QUERY = "https%3A%2F%2Fskillarcade.local"

fun extractYouTubeVideoId(youtubeUrl: String): String? {
    val uri = runCatching { URI(youtubeUrl.trim()) }.getOrNull() ?: return null
    val host = uri.host?.lowercase()?.removePrefix("www.") ?: return null
    val pathSegments = uri.pathSegments()

    val videoId = when (host) {
        "youtu.be" -> pathSegments.firstOrNull()
        "youtube.com", "m.youtube.com", "music.youtube.com" -> when (pathSegments.firstOrNull()) {
            "watch" -> uri.queryParameter("v")
            "embed", "shorts" -> pathSegments.getOrNull(1)
            else -> null
        }
        else -> null
    }

    return videoId?.takeIf { it.isNotBlank() }
}

fun buildYouTubeEmbedUrl(videoId: String): String =
    "https://www.youtube.com/embed/$videoId?playsinline=1&rel=0&controls=1&enablejsapi=1&origin=$YOUTUBE_PLAYER_ORIGIN_QUERY"

fun buildYouTubePlayerHtml(videoId: String): String {
    return """
        <!DOCTYPE html>
        <html>
        <head>
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <style>
                html,
                body {
                    width: 100%;
                    height: 100%;
                    margin: 0;
                    padding: 0;
                    overflow: hidden;
                    background: #000000;
                }

                iframe {
                    display: block;
                    width: 100%;
                    height: 100%;
                    border: 0;
                    background: #000000;
                }
            </style>
        </head>
        <body>
            <iframe
                src="${buildYouTubeEmbedUrl(videoId)}"
                allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" 
                allowfullscreen>
            </iframe>
        </body>
        </html>
    """.trimIndent()
}

private fun URI.pathSegments(): List<String> =
    rawPath.orEmpty()
        .split("/")
        .filter { it.isNotBlank() }
        .map { it.urlDecode() }

private fun URI.queryParameter(name: String): String? =
    rawQuery.orEmpty()
        .split("&")
        .mapNotNull { part ->
            val key = part.substringBefore("=").urlDecode()
            val value = part.substringAfter("=", missingDelimiterValue = "").urlDecode()
            key to value
        }
        .firstOrNull { (key, _) -> key == name }
        ?.second

private fun String.urlDecode(): String =
    URLDecoder.decode(this, "UTF-8")
