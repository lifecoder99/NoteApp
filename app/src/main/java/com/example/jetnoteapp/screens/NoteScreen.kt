package com.example.jetnoteapp.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetnoteapp.R
import com.example.jetnoteapp.components.NoteButton
import com.example.jetnoteapp.components.NoteInputText
import com.example.jetnoteapp.data.NoteDataSource
import com.example.jetnoteapp.model.Note
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(
    notes: List<Note> = emptyList(),
    onAddNote: (Note) -> Unit = {},
    onRemoveNote: (Note) -> Unit = {},
) {

    var title by remember {
        mutableStateOf("")
    }

    var description by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current

    Column(modifier = Modifier.padding(6.dp)) {
        Scaffold(topBar = {
            TopAppBar(title = { Text(text = stringResource(id = R.string.app_name)) },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.LightGray),
                actions = {
                    Icon(
                        imageVector = Icons.Rounded.Notifications,
                        contentDescription = "Notification"
                    )
                })

        }) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                NoteInputText(
                    text = title, label = "Title", maxLine = 1, onTextChange = {
                        if (it.all { char: Char ->
                                char.isLetter() || char.isWhitespace()
                            }) {
                            title = it
                        }
                    }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = 8.dp
                        )
                ) {}

                NoteInputText(
                    text = description, label = "Add a note", maxLine = 1, onTextChange = {
                        if (it.all { char: Char ->
                                char.isLetter() || char.isWhitespace()
                            }) {
                            description = it
                        }
                    }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = 8.dp
                        )
                ) {

                }
                NoteButton(text = "Save", onClick = {
                    if (title.isNotEmpty() || description.isNotEmpty()) {
                        onAddNote(Note(title= title, description = description))
                        title =""
                        description =""
                        Toast.makeText(context, "Note Added", Toast.LENGTH_LONG).show()
                    }
                })

                Divider(modifier = Modifier.padding(10.dp))
                LazyColumn {
                    items(notes) { note ->
                        NoteRow(note= note, onNoteClicked = {
                            onRemoveNote(it)
                        })
                    }
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteRow(
    modifier: Modifier = Modifier,
    note: Note = NoteDataSource().loadNotes().first(),
    onNoteClicked: (Note) -> Unit={},
) {
    Surface(modifier = modifier
        .padding(4.dp)
        .fillMaxWidth(),
        shape = RoundedCornerShape(topEnd = 33.dp, bottomStart = 33.dp),
        color = Color(0xFFDFE6EB),
        shadowElevation = 6.dp,
        onClick = {
            onNoteClicked(note)
        }
    ) {
        Column(modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
            horizontalAlignment = Alignment.Start) {
            Text(text = note.title, style = MaterialTheme.typography.titleMedium
                )
            Text(text = note.description, style = MaterialTheme.typography.titleSmall
            )
            Text(text = note.entityData.format(DateTimeFormatter.ofPattern("EEE, dd MMM YYYY")),
                style = MaterialTheme.typography.labelSmall

            )


        }

    }
}

@Preview(showBackground = true)
@Composable
fun NoteScreenPreview() {
    NoteScreen(NoteDataSource().loadNotes())
}