package com.nanit.babybirthday.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Baby(
    val name: String,
    val dateOfBirth: Long,
    val picture: String? = null,
)
