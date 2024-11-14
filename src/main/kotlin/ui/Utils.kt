package ui

import kotlin.io.path.Path

fun resPath(name: String): String {
    return Path("img", name).toString()
}