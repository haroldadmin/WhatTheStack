package com.haroldadmin.whatthestack

import java.net.URLEncoder

fun generateStackoverflowSearchUrl(query: String): String {
    val baseUrl = "https://stackoverflow.com/search"
    val queryString = URLEncoder.encode(query, "utf-8")
    return "$baseUrl?q=$queryString"
}
