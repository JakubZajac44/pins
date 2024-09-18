package com.jakub.zajac.storage.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PinEntity (
    @PrimaryKey(autoGenerate = true)
    val key: Int? = null,
    val pinCode: String,
    val name: String
)