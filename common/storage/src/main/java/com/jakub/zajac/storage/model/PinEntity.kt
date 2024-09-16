package com.jakub.zajac.storage.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PinEntity (
    @PrimaryKey(autoGenerate = true)
    val key: Int,
    val pinCode: Int,
    val name: String
)