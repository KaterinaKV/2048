package com.app.android2048.util

import android.content.Context
import com.app.android2048.model.Record
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.InputStreamReader

internal object JSONHelper {
    private const val FILE_NAME = "data.json"

    fun exportToJSON(context: Context, recordsList: List<Record>?) {
        val gson = Gson()
        val dataItems = DataItems()
        dataItems.records = recordsList
        val jsonString: String = gson.toJson(dataItems.records)
        val fileOutputStream: FileOutputStream = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)
        fileOutputStream.use { it.write(jsonString.toByteArray()) }
    }

    fun importFromJSON(context: Context): List<Record>? {
        val fileInputStream: FileInputStream?
        val streamReader: InputStreamReader
        return try {
            fileInputStream = context.openFileInput(FILE_NAME)
            streamReader = InputStreamReader(fileInputStream)
            val gson = Gson()
            val arrayTutorialType = object : TypeToken<List<Record>>() {}.type
            var listRec: List<Record> = gson.fromJson(streamReader, arrayTutorialType)
            println(listRec)
            listRec
        } catch (ex : FileNotFoundException) {
            null
        }
    }

    private class DataItems {
        var records: List<Record>? = null
    }
}