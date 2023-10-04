package com.example.jetnoteapp.model

import android.icu.util.LocaleData
import androidx.compose.ui.text.intl.Locale
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

data class Note(
    var id:UUID = UUID.randomUUID(),
    val title:String="",
    val description:String="",
    val entityData : LocalDateTime = LocalDateTime.now()
)
