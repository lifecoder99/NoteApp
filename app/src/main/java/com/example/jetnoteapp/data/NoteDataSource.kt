package com.example.jetnoteapp.data

import com.example.jetnoteapp.model.Note

class NoteDataSource{
    fun loadNotes():List<Note>{
        return listOf(
            Note(
                title = "A Good Day", description = "Yes it Good day"
            ),
            Note(
                title = "A Good Night", description = "Yes it Good night"
            ),
        )
    }
}