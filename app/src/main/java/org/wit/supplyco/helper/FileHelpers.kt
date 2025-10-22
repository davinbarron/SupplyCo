package org.wit.supplyco.helper

import android.content.Context
import timber.log.Timber.i
import timber.log.Timber.e
import java.io.*

/**
 * FileHelpers is a utility class for reading and writing JSON files in Android.
 * Modified to use Timber logging
 * Resources for file handling:
 * https://medium.com/@YodgorbekKomilo/day-18-file-handling-in-kotlin-reading-writing-files-made-simple-da774d449459
 * https://cdpateldigitalroom.medium.com/files-in-android-read-write-append-data-using-kotlin-7a6b2d70de1c
 */

fun write(context: Context, fileName: String, data: String) {
    try {
        val file = File(context.filesDir, fileName)
        file.writeText(data)
        i("Successfully wrote to $fileName")
    } catch (e: Exception) {
        e("Error writing to file $fileName: ${e.message}")
    }
}

fun read(context: Context, fileName: String): String {
    return try {
        val file = File(context.filesDir, fileName)
        val content = file.readText()
        i("Successfully read from $fileName")
        content
    } catch (e: FileNotFoundException) {
        e("File not found: $fileName: ${e.message}")
        ""
    } catch (e: IOException) {
        e("Error reading file $fileName: ${e.message}")
        ""
    }
}

fun exists(context: Context, fileName: String): Boolean {
    val file = File(context.filesDir, fileName)
    val exists = file.exists()
    i("File $fileName exists: $exists")
    return exists
}