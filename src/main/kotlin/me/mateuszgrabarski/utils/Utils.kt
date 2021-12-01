package me.mateuszgrabarski.utils

import java.io.File

class Utils

fun readFile(name: String): List<String> =
    File(Utils::class.java.classLoader.getResource(name).toURI()).readLines()