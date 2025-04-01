package com.nanit.babybirthday.data.model

import androidx.datastore.core.Serializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object BabySerializer : Serializer<Baby> {
    override val defaultValue: Baby
        get() = Baby("", 0L, null)

    override suspend fun readFrom(input: InputStream): Baby = withContext(Dispatchers.IO) {
        try {
            Json.decodeFromString(
                deserializer = Baby.serializer(), string = input.readBytes().decodeToString()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: Baby, output: OutputStream) {
        withContext(Dispatchers.IO) {
            output.write(
                Json.encodeToString(
                    serializer = Baby.serializer(), value = t
                ).encodeToByteArray()
            )
        }
    }
}